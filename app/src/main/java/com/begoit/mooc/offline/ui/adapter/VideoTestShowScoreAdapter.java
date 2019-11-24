package com.begoit.mooc.offline.ui.adapter;

import android.graphics.Color;
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
 * @Description:测试题答案解析适配器
 * @Author:gxj
 * @Time 2019/4/22
 */

public class VideoTestShowScoreAdapter extends BaseMultiItemQuickAdapter<VideoTestAnswerOptionEntity,BaseViewHolder> {

    private WebSettings webSettings;
    private WebView webView;
    private String courseId;

    public VideoTestShowScoreAdapter(List<VideoTestAnswerOptionEntity> data) {
        super(data);
        addItemType(0, R.layout.item_test_answer_status_1);
        addItemType(1, R.layout.item_test_answer_status_1);
        addItemType(2, R.layout.item_test_answer_status_1);
        addItemType(3, R.layout.item_test_answer_status_3);
    }

    /**
     * 设置课程ID
     * @param courseId
     */
    public void setCourseId(String courseId){
        this.courseId = courseId;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final VideoTestAnswerOptionEntity item) {
        switch (item.testType){
            case 0:
            case 1:
                if (item.userAnswer != null && item.userAnswer.equals(item.option)) {
                    if (item.userAnswer.equals(item.answer)) {
                        helper.setBackgroundRes(R.id.iv_select, R.drawable.ic_button_radio_selected_right);
                    }else {
                        helper.setBackgroundRes(R.id.iv_select, R.drawable.ic_button_radio_error);
                    }
                }else {
                    helper.setBackgroundRes(R.id.iv_select,R.drawable.ic_button_radio_nomal);
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
                break;
            case 2:
                if (item.userAnswer != null && item.userAnswer.contains(item.option)) {
                    if (item.answer.contains(item.option)) {
                        helper.setBackgroundRes(R.id.iv_select, R.drawable.ic_button_checkbox_selected_right);
                    }else {
                        helper.setBackgroundRes(R.id.iv_select, R.drawable.ic_button_checkbox_selected_error);
                    }
                }else {
                    helper.setBackgroundRes(R.id.iv_select,R.drawable.ic_button_checkbox_nomal);
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
                break;
            case 3:
                helper.setText(R.id.tv_option,item.option + ".");

                helper.setText(R.id.tv_answer,item.content);

                if (isRightAnswer(item,item.content)){
                    helper.setTextColor(R.id.tv_answer, Color.GREEN);
                }else {
                    helper.setTextColor(R.id.tv_answer, Color.RED);
                }
                break;
        }
    }

    private boolean isRightAnswer(VideoTestAnswerOptionEntity item,String userAnswerOption){
        String[] fillBlankAnswers = item.answer.split("`");
        if (fillBlankAnswers[getPosition(item)].equals(userAnswerOption)){
            return true;
        }else {
            return false;
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
    @Override
    public int getItemViewType(int position) {
        return getItem(position).testType;
    }

}
