package com.ttg.ecollection.ui;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.ttg.ecollection.R;
import com.ttg.ecollection.base.BaseFragment;
import com.ttg.ecollection.data.OrderStatusData;
import com.ttg.ecollection.data.OrdersData;
import com.ttg.ecollection.data.PayResult;
import com.ttg.ecollection.presenter.ShowCodePresenter;
import com.ttg.ecollection.util.UIUtil;
import com.ttg.ecollection.view.IShowCodeView;

/**
 * Created by loveb on 2018/3/20 0020.
 */

public class ShowCodeFragment extends BaseFragment<ShowCodePresenter> implements IShowCodeView {

    private ShowCodePresenter presenter;
    private ImageView codeImage;
//    private String orderNo;
    private OrderStatusData data;

    @Override
    public ShowCodePresenter initPresent() {
        presenter = new ShowCodePresenter(this);
        return presenter;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_show_code;
    }

    @Override
    public void initView(View view) {
        RelativeLayout back = getActivity().findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);

        ((TextView) getActivity().findViewById(R.id.title)).setText(R.string.confirm_transaction);

        TextView amount = getView(view,R.id.tv_amount);
        codeImage = getView(view,R.id.iv_code);

        getView(view,R.id.tv_refresh).setOnClickListener(this);

        data = getArguments().getParcelable("data");

        amount.setText(String.valueOf(data.getEpayAmt()));


        presenter.getQrCode(getContext(),data.getPayType(),data.getOrderId());

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.back:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.tv_refresh:
                presenter.getQrCode(getContext(),data.getPayType(),data.getOrderId());
                break;
        }
    }

    @Override
    public void setCode(String code) {
        if (Build.VERSION.SDK_INT >= 16 ){
            codeImage.setBackground(new BitmapDrawable(encodeAsBitmap(code,UIUtil.dp2px(getActivity(),180),UIUtil.dp2px(getActivity(),180))));
        }else{
            codeImage.setBackgroundDrawable(new BitmapDrawable(encodeAsBitmap(code,UIUtil.dp2px(getActivity(),180),UIUtil.dp2px(getActivity(),180))));
        }

        presenter.queryPayResult(getActivity());

    }

    @Override
    public void jumpToSuccess(PayResult result) {

        OrdersData.ResultBean resultBean = new OrdersData.ResultBean();
        resultBean.setOrderId(result.getOrderId());
        resultBean.setEpayAmt(result.getEpayAmt());
        resultBean.setCashAmt(result.getCashAmt());
        resultBean.setPosAmt(result.getPostAmt());
        resultBean.setModifyTime(result.getOrderTime());
        resultBean.setCompanyName(data.getCompanyName());
        resultBean.setOrderStatus(result.getOrderStatus());
        resultBean.setPayName(result.getPayName());

        Fragment fragment = new RecordDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("data",resultBean);
        bundle.putBoolean("fromCash",true);
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Nullable
    private Bitmap encodeAsBitmap(String str, int width, int height){
        try {
//            Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
//            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
//            hints.put(EncodeHintType.MARGIN, 1);
            BitMatrix matrix = new QRCodeWriter().encode(str, BarcodeFormat.QR_CODE, width, height);
            matrix = deleteWhite(matrix);//删除白边
            width = matrix.getWidth();
            height = matrix.getHeight();
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (matrix.get(x, y)) {
                        pixels[y * width + x] = Color.BLACK;
                    } else {
                        pixels[y * width + x] = Color.WHITE;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    private static BitMatrix deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1]))
                    resMatrix.set(i, j);
            }
        }
        return resMatrix;
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.destroy();
    }
}
