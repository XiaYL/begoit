package com.begoit.mooc.offline.ui.adapter;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;

import com.begoit.mooc.offline.R;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.VideoTestAnswerOptionEntity;
import com.begoit.mooc.offline.utils.FileHelper;
import com.begoit.mooc.offline.utils.FormatStringUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @Description:测试题答案选项适配器
 * @Author:gxj
 * @Time 2019/4/22
 */

public class VideoTestAnswerOptionsAdapter extends BaseMultiItemQuickAdapter<VideoTestAnswerOptionEntity,BaseViewHolder> {

    private WebSettings webSettings;
    private WebView webView;
    private String courseId;
    private OnSelectAnswerListener onSelectAnswerListener;
    private OnTextChangedListener onTextChangedListener;

    public VideoTestAnswerOptionsAdapter(List<VideoTestAnswerOptionEntity> data) {
        super(data);
        addItemType(0, R.layout.item_test_answer_0);
        addItemType(1, R.layout.item_test_answer_1);
        addItemType(2, R.layout.item_test_answer_2);
        addItemType(3, R.layout.item_test_answer_3);
    }

    /**
     * 设置课程ID
     * @param courseId
     */
    public void setCourseId(String courseId){
        this.courseId = courseId;
    }

    /**
     * 设置选择题判断题答题监听
     * @param listener
     */
    public void setOnSelectAnswerListener(OnSelectAnswerListener listener){
        this.onSelectAnswerListener = listener;
    }

    /**
     * 填空题答题监听
     * @param listener
     */
    public void setOnTextChangedListener(OnTextChangedListener listener){
        this.onTextChangedListener = listener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final VideoTestAnswerOptionEntity item) {
        switch (item.testType){
            case 0:
                if (!TextUtils.isEmpty(item.userAnswer) && item.userAnswer.equals(item.option)){
                    helper.setChecked(R.id.cb_select,true);
                }else {
                    helper.setChecked(R.id.cb_select,false);
                }
                helper.setText(R.id.tv_option,item.option + ".");
                webView = helper.getView(R.id.wv_answer);
                webSettings = webView.getSettings();
                //允许webview对文件的操作
                webSettings.setAllowUniversalAccessFromFileURLs(true);
                webSettings.setAllowFileAccess(true);
                webSettings.setAllowFileAccessFromFileURLs(true);
                webSettings.setJavaScriptEnabled(true);//启用js
                webSettings.setBlockNetworkImage(false);//解决图片不显示
                webView.loadDataWithBaseURL(FileHelper.formatBaseUrl(courseId), FormatStringUtils.fillAllHtmlForImgFromLocal(item.content,courseId)
                        , "text/html", "utf-8", null);
                helper.setOnClickListener(R.id.cb_select, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        answerSelection(true,item.option);
                    }
                });
                break;
            case 1:
                if (!TextUtils.isEmpty(item.userAnswer) && item.userAnswer.equals(item.option)){
                    helper.setChecked(R.id.cb_select,true);
                }else {
                    helper.setChecked(R.id.cb_select,false);
                }
                helper.setText(R.id.tv_option,item.option + ".");
                webView = helper.getView(R.id.wv_answer);
                webSettings = webView.getSettings();
                //允许webview对文件的操作
                webSettings.setAllowUniversalAccessFromFileURLs(true);
                webSettings.setAllowFileAccess(true);
                webSettings.setAllowFileAccessFromFileURLs(true);
                webSettings.setJavaScriptEnabled(true);//启用js
                webSettings.setBlockNetworkImage(false);//解决图片不显示
                webView.loadDataWithBaseURL(FileHelper.formatBaseUrl(courseId), FormatStringUtils.fillAllHtmlForImgFromLocal(item.content,courseId)
                        , "text/html", "utf-8", null);
                helper.setOnClickListener(R.id.cb_select, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        answerSelection(true,item.option);
                    }
                });
                break;
            case 2:
                if (!TextUtils.isEmpty(item.userAnswer) && item.userAnswer.contains(item.option)){
                    helper.setChecked(R.id.cb_select,true);
                }else {
                    helper.setChecked(R.id.cb_select,false);
                }
                helper.setText(R.id.tv_option,item.option + ".");
                webView = helper.getView(R.id.wv_answer);
                webSettings = webView.getSettings();
                //允许webview对文件的操作
                webSettings.setAllowUniversalAccessFromFileURLs(true);
                webSettings.setAllowFileAccess(true);
                webSettings.setAllowFileAccessFromFileURLs(true);
                webSettings.setJavaScriptEnabled(true);//启用js
                webSettings.setBlockNetworkImage(false);//解决图片不显示
                webView.loadDataWithBaseURL(FileHelper.formatBaseUrl(courseId), FormatStringUtils.fillAllHtmlForImgFromLocal(item.content,courseId)
                        , "text/html", "utf-8", null);
                helper.setOnClickListener(R.id.cb_select, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        answerSelection(false,item.option);
                    }
                });
                break;
            case 3:
                helper.setText(R.id.tv_option,item.option + ".");
                if (TextUtils.isEmpty(item.content)){
                    helper.setText(R.id.et_answer,"");
                }else {
                    helper.setText(R.id.et_answer,item.content);
                }
                final TextWatcher textWatcher=new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if((helper.getView((R.id.et_answer)).hasFocus())){//判断当前EditText是否有焦点在
                            //通过接口回调将数据传递到Activity中
                            onTextChangedListener.onTextChanged(getPosition(item),((EditText)helper.getView((R.id.et_answer))).getText().toString());
                        }
                    }
                };

                (helper.getView((R.id.et_answer))).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(hasFocus){
                            ((EditText)helper.getView((R.id.et_answer))).addTextChangedListener(textWatcher);
                        }else{
                            ((EditText)helper.getView((R.id.et_answer))).removeTextChangedListener(textWatcher);
                        }
                    }
                });

                break;
        }
    }

    private int getPosition(VideoTestAnswerOptionEntity item){
        for (int i = 0; i < mData.size();i++){
            if (item.id == mData.get(i).id){
                return i;
            }
        }
        return -1;
    }

    /**
     * 答案选择逻辑
     * @param option
     */
    private void answerSelection(boolean isRadio,String option){
        onSelectAnswerListener.onSelect(isRadio,option);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).testType;
    }

    //选择题选项被选择监听
    public interface OnSelectAnswerListener{
         void onSelect(boolean isRadio,String userAswer);
    }

    //填空题答题监听
    public interface OnTextChangedListener{
        void onTextChanged(int position,String text);
    }
}
