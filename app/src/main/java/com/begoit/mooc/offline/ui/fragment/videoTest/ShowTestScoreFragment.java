package com.begoit.mooc.offline.ui.fragment.videoTest;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.begoit.mooc.offline.R;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.VideoTestAnswerOptionEntity;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.VideoTestEntity;
import com.begoit.mooc.offline.ui.activity.learning.event.TestAginOrWatchVideoEvent;
import com.begoit.mooc.offline.ui.adapter.ShowTestAnswerStatusAdapter;
import com.begoit.mooc.offline.ui.adapter.VideoTestShowScoreAdapter;
import com.begoit.mooc.offline.utils.DeviceInfoUtil;
import com.begoit.mooc.offline.utils.FileHelper;
import com.begoit.mooc.offline.utils.FormatStringUtils;
import com.begoit.mooc.offline.utils.KeyboardUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @Description:考核结果展示
 * @Author:gxj
 * @Time 2019/4/19
 */

@SuppressLint("ValidFragment")
public class ShowTestScoreFragment extends Fragment implements ShowTestAnswerStatusAdapter.OnItemClickListener,View.OnClickListener {
    private View rootView;
    //题号
    @BindView(R.id.tv_position)
    TextView tvPosition;
    //题型
    @BindView(R.id.tv_test_style)
    TextView tvTestStyle;
    //分数
    @BindView(R.id.tv_test_score)
    TextView tvTestScore;
    //上一题
    @BindView(R.id.iv_previous)
    ImageView ivPrevious;
    //下一题
    @BindView(R.id.iv_next)
    ImageView ivNext;

    @BindView(R.id.wv_testtitle)
    WebView wvTestTitle;
    @BindView(R.id.rl_test_answer_option)
    RecyclerView answerOptions;
    //结果展示View
    @BindView(R.id.rl_score_container)
    RelativeLayout rlScoreContainer;
    //得分
    @BindView(R.id.tv_has_score)
    TextView tvHasScore;
    //总分
    @BindView(R.id.tv_total_score)
    TextView tvTotalScore;
    //测试题列表
    private List<VideoTestEntity> testList;
    private int currentPosition = 0;//当前在做题目下表
    protected Unbinder unbinder;
    private String courseId;
    private String videoId;
    private int hasScore;
    private int totalScore;
    private VideoTestShowScoreAdapter answerOptionsAdapter;

    //试题目录，展示答题状态
    private PopupWindow testListStatusPop;
    private View popView;
    private RecyclerView recyclerView;
    private ShowTestAnswerStatusAdapter testListStatusAdapter;
    //查看答题状态
    @OnClick(R.id.make_status)
    public void showMakeStatus(){
        KeyboardUtils.hideSoftInput(getActivity());
       if (testListStatusPop == null || popView == null || recyclerView == null || testListStatusAdapter == null){
           popView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_show_test_answer_status,null);
           recyclerView = popView.findViewById(R.id.recyclerView);
           popView.findViewById(R.id.iv_close).setOnClickListener(this);
           testListStatusPop = new PopupWindow(popView, DeviceInfoUtil.getDevicesWidth(),DeviceInfoUtil.getDeviceHeight());
           testListStatusPop.setFocusable(true);
           testListStatusAdapter = new ShowTestAnswerStatusAdapter(testList,getActivity());
           testListStatusAdapter.setItemClickListener(this);
           GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),6);
           recyclerView.setLayoutManager(layoutManager);
           recyclerView.setAdapter(testListStatusAdapter);
       }else {
           testListStatusAdapter.changeData(testList);
       }
       if (testListStatusPop.isShowing()){
           testListStatusPop.dismiss();
       }else {
           testListStatusPop.showAtLocation(getView(), Gravity.CENTER, 0, 0);
       }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close:
                if (testListStatusPop != null) {
                    testListStatusPop.dismiss();
                }
                break;
        }
    }

    //下一题
    @OnClick(R.id.iv_next)
    public void goNext(){
        KeyboardUtils.hideSoftInput(getActivity());
       if (testList != null && testList.size() > currentPosition){
           currentPosition++;
           showTest(testList.get(currentPosition));
           isShowChangeTestView();
       }
    }
    //上一题
    @OnClick(R.id.iv_previous)
    public void goPrevious(){
        KeyboardUtils.hideSoftInput(getActivity());
        if (currentPosition > 0 && testList != null && testList.size() >= currentPosition + 1){
            currentPosition--;
            showTest(testList.get(currentPosition));
        }
        isShowChangeTestView();
    }

    @OnClick(R.id.tv_parse)
    public void onParse(){
        rlScoreContainer.setVisibility(View.GONE);
    }

    //继续学习
    @OnClick(R.id.tv_continue)
    public void onContinue(){
        EventBus.getDefault().post(new TestAginOrWatchVideoEvent(false));
    }

    @OnClick(R.id.tv_test_agin)
    public void onTestAgin(){
        EventBus.getDefault().post(new TestAginOrWatchVideoEvent(true));
    }

    //从查看答题状态跳转到某题
    @Override
    public void onClick(int position) {
        if (position < 0){
            return;
        }
        if (testListStatusPop != null) {
            testListStatusPop.dismiss();
        }
        KeyboardUtils.hideSoftInput(getActivity());
        if (testList != null && testList.size() > position){
            currentPosition = position;
            showTest(testList.get(currentPosition));
            isShowChangeTestView();
        }
    }

    //上一题下一题按钮显隐逻辑
    private void isShowChangeTestView(){
        if (currentPosition > 0){
            ivPrevious.setVisibility(View.VISIBLE);
        }else{
            ivPrevious.setVisibility(View.INVISIBLE);
        }
        if (testList.size() > currentPosition + 1){
            ivNext.setVisibility(View.VISIBLE);
        }else{
            ivNext.setVisibility(View.INVISIBLE);
        }

    }

    @SuppressLint("ValidFragment")
    public ShowTestScoreFragment(List<VideoTestEntity> datas, String courseId,int hasScore,int totalScore){
        testList = datas;
        this.hasScore = hasScore;
        this.totalScore = totalScore;
        videoId = testList.get(0).videoId;
        this.courseId = courseId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_show_test_score, container, false);
        }
        unbinder = ButterKnife.bind(this,rootView);
        answerOptions.setNestedScrollingEnabled(false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tvHasScore.setText(String.valueOf(hasScore));
        tvTotalScore.setText("题目总分" + totalScore + "分");
        WebSettings webSettings = wvTestTitle.getSettings();
        //允许webview对文件的操作
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setJavaScriptEnabled(true);//启用js
        webSettings.setBlockNetworkImage(false);//解决图片不显示

        if (testList != null && testList.size() > 0) {
            ivPrevious.setVisibility(View.INVISIBLE);
            if (testList.size() == 1){
                ivNext.setVisibility(View.INVISIBLE);
            }
            showTest(testList.get(0));
        }
    }

    /**
     * 展示题目和答案选项
     * @param item
     */
    private void showTest(VideoTestEntity item){
        tvPosition.setText(String.valueOf(currentPosition + 1));
        tvTestStyle.setText(getTestStyle(item));
        tvTestScore.setText("(" + item.score + "分)");
        if (item.testTitle == null){
            wvTestTitle.loadData("", "text/html; charset=UTF-8", null);
        }else {
            wvTestTitle.loadDataWithBaseURL(FileHelper.formatBaseUrl(courseId), FormatStringUtils.fillAllHtmlForImgFromLocal(item.testTitle,courseId)
                    , "text/html", "utf-8", null);
        }
        if (answerOptionsAdapter == null){
            answerOptionsAdapter = new VideoTestShowScoreAdapter(getAnswerOptions(item));
            answerOptionsAdapter.setCourseId(courseId);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
            answerOptions.setLayoutManager(layoutManager);
            answerOptions.setAdapter(answerOptionsAdapter);
        }else {
            answerOptionsAdapter.setNewData(getAnswerOptions(item));
        }
    }
    //获取测试题类型
    private String getTestStyle(VideoTestEntity item){
        if (item.testType == 0){
            return  "【判断题】";
        } else if (item.testType == 1){
            return  "【单选题】";
        } else if (item.testType == 2){
            return  "【多选题】";
        } else if (item.testType == 3){
            return  "【填空题】";
        }
        return "";
    }

    //获取答案选项列表
   private List<VideoTestAnswerOptionEntity> getAnswerOptions(VideoTestEntity item){
       List<VideoTestAnswerOptionEntity> answers = new ArrayList<>();
       VideoTestAnswerOptionEntity temp;
       if (item.testType == 3){
           for (int i = 0;i < item.answer.split("`").length;i++){

               String[] fillBlankUserAnswers;
               if (TextUtils.isEmpty(item.userAnswer)){
                   fillBlankUserAnswers = new String[item.answer.split("`").length];
               }else{
                   fillBlankUserAnswers = item.userAnswer.split("`");
               }
               temp = new VideoTestAnswerOptionEntity(i + 1,item.testType, String.valueOf(i + 1)
                       , (TextUtils.isEmpty(fillBlankUserAnswers[i]) ? "":fillBlankUserAnswers[i]));
               temp.userAnswer = item.userAnswer;
               temp.answer = item.answer;
               answers.add(temp);
           }
       }else {
           if (!TextUtils.isEmpty(item.questionA)) {
               temp = new VideoTestAnswerOptionEntity(1,item.testType, "A", item.questionA);
               temp.userAnswer = item.userAnswer;
               temp.answer = item.answer;
               answers.add(temp);
           }
           if (!TextUtils.isEmpty(item.questionB)) {
               temp = new VideoTestAnswerOptionEntity(2,item.testType, "B", item.questionB);
               temp.userAnswer = item.userAnswer;
               temp.answer = item.answer;
               answers.add(temp);
           }
           if (!TextUtils.isEmpty(item.questionC)) {
               temp = new VideoTestAnswerOptionEntity(3,item.testType, "C", item.questionC);
               temp.userAnswer = item.userAnswer;
               temp.answer = item.answer;
               answers.add(temp);
           }
           if (!TextUtils.isEmpty(item.questionD)) {
               temp = new VideoTestAnswerOptionEntity(4,item.testType, "D", item.questionD);
               temp.userAnswer = item.userAnswer;
               temp.answer = item.answer;
               answers.add(temp);
           }
           if (!TextUtils.isEmpty(item.questionE)) {
               temp = new VideoTestAnswerOptionEntity(5,item.testType, "E", item.questionE);
               temp.userAnswer = item.userAnswer;
               temp.answer = item.answer;
               answers.add(temp);
           }
           if (!TextUtils.isEmpty(item.questionF)) {
               temp = new VideoTestAnswerOptionEntity(6,item.testType, "F", item.questionF);
               temp.userAnswer = item.userAnswer;
               temp.answer = item.answer;
               answers.add(temp);
           }
           if (!TextUtils.isEmpty(item.questionG)) {
               temp = new VideoTestAnswerOptionEntity(7,item.testType, "G", item.questionG);
               temp.userAnswer = item.userAnswer;
               temp.answer = item.answer;
               answers.add(temp);
           }
           if (!TextUtils.isEmpty(item.questionH)) {
               temp = new VideoTestAnswerOptionEntity(8,item.testType, "H", item.questionH);
               temp.userAnswer = item.userAnswer;
               temp.answer = item.answer;
               answers.add(temp);
           }
           if (!TextUtils.isEmpty(item.questionI)) {
               temp = new VideoTestAnswerOptionEntity(9,item.testType, "I", item.questionI);
               temp.userAnswer = item.userAnswer;
               temp.answer = item.answer;
               answers.add(temp);
           }
           if (!TextUtils.isEmpty(item.questionJ)) {
               temp = new VideoTestAnswerOptionEntity(10,item.testType, "J", item.questionJ);
               temp.userAnswer = item.userAnswer;
               temp.answer = item.answer;
               answers.add(temp);
           }
       }
       return answers;
   }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
