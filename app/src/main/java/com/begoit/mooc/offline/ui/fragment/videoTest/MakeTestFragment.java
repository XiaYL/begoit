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
import com.begoit.mooc.offline.ui.activity.learning.event.ShowScoreEvent;
import com.begoit.mooc.offline.ui.adapter.TestListStatusAdapter;
import com.begoit.mooc.offline.ui.adapter.VideoTestAnswerOptionsAdapter;
import com.begoit.mooc.offline.utils.DeviceInfoUtil;
import com.begoit.mooc.offline.utils.FileHelper;
import com.begoit.mooc.offline.utils.FormatStringUtils;
import com.begoit.mooc.offline.utils.GsonUtil;
import com.begoit.mooc.offline.utils.KeyboardUtils;
import com.begoit.mooc.offline.widget.basedialog.DialogUtils;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @Description:考核答题
 * @Author:gxj
 * @Time 2019/4/19
 */

@SuppressLint("ValidFragment")
public class MakeTestFragment extends Fragment implements VideoTestAnswerOptionsAdapter.OnSelectAnswerListener
        ,VideoTestAnswerOptionsAdapter.OnTextChangedListener,TestListStatusAdapter.OnItemClickListener,View.OnClickListener {
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

    @BindView(R.id.rl_container)
    RelativeLayout container;
    //题干内容
    @BindView(R.id.wv_testtitle)
    WebView wvTestTitle;
    @BindView(R.id.rl_test_answer_option)
    RecyclerView answerOptions;
    //提交
    @BindView(R.id.tv_commit_test)
    TextView tvCommitTest;
    //测试题列表
    private List<VideoTestEntity> testList;
    private int currentPosition = 0;//当前在做题目下表
    protected Unbinder unbinder;
    private String courseId;
    private String videoId;
    private VideoTestAnswerOptionsAdapter answerOptionsAdapter;

    private String[] fillBlankAnswers;//填空题答题过程记录

    //试题目录，展示答题状态
    private PopupWindow testListStatusPop;
    private View popView;
    private RecyclerView recyclerView;
    private TestListStatusAdapter testListStatusAdapter;
    //目录查看答题状态
    @OnClick(R.id.make_status)
    public void showMakeStatus(){
        KeyboardUtils.hideSoftInput(getActivity());
       if (testListStatusPop == null || popView == null || recyclerView == null || testListStatusAdapter == null){
           popView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_test_status,null);
           recyclerView = popView.findViewById(R.id.recyclerView);
           popView.findViewById(R.id.iv_close).setOnClickListener(this);
           popView.findViewById(R.id.tv_commit).setOnClickListener(this);
           testListStatusPop = new PopupWindow(popView, DeviceInfoUtil.getDevicesWidth(),DeviceInfoUtil.getDeviceHeight());
           testListStatusPop.setFocusable(true);
           testListStatusAdapter = new TestListStatusAdapter(testList,getActivity());
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
            case R.id.tv_commit:
            case R.id.tv_commit_test:
                commitTes();
                break;
        }
    }
    //提交考核
    private void commitTes(){
        DialogUtils.getInstance().showGenericDialogForTwoButtonCommon(getActivity(), "确认提交答案吗？", null, "取消", "确认"
                , new DialogUtils.ButtonClickCallBack() {
            @Override
            public void clickCallBack(View view) {
                int hasScore = 0;
                int totalScore = 0;
                List<VideoTestEntity> list;
                String json = GsonUtil.getInstance().toJson(testList);
                list = GsonUtil.getInstance().fromJson(json, new TypeToken<List<VideoTestEntity>>() {}.getType());
                for (VideoTestEntity entity:testList){
                    totalScore = totalScore + entity.score;
                    if (entity.answer.equals(entity.userAnswer)){
                        hasScore = hasScore + entity.score;
                    }
                    entity.userAnswer = null;
                }
                EventBus.getDefault().post(new ShowScoreEvent(hasScore,totalScore,list));
                if (testListStatusPop != null) {
                    testListStatusPop.dismiss();
                }
            }
        });
    }
    //下一题
    @OnClick(R.id.iv_next)
    public void goNext(){
        KeyboardUtils.hideSoftInput(getActivity());
       if (testList != null && testList.size() > currentPosition){
           fillBlankAnswers = null;
           currentPosition++;
           showTest(testList.get(currentPosition));
           isShowChangeTestView();
       }
    }
    //上一题
    @OnClick(R.id.iv_previous)
    public void goPrevious(){
        KeyboardUtils.hideSoftInput(getActivity());
        fillBlankAnswers = null;
        if (currentPosition > 0 && testList != null && testList.size() >= currentPosition + 1){
            currentPosition--;
            showTest(testList.get(currentPosition));
        }
        isShowChangeTestView();
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
            fillBlankAnswers = null;
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
            tvCommitTest.setVisibility(View.GONE);
        }else{
            ivNext.setVisibility(View.INVISIBLE);
            tvCommitTest.setVisibility(View.VISIBLE);
        }

    }

    @SuppressLint("ValidFragment")
    public MakeTestFragment(List<VideoTestEntity> datas,String courseId){
        testList = datas;
        videoId = testList.get(0).videoId;
        this.courseId = courseId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_make_test, container, false);
        }
        unbinder = ButterKnife.bind(this,rootView);
        answerOptions.setNestedScrollingEnabled(false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        wvTestTitle.setHorizontalScrollBarEnabled(false);//水平不显示
        wvTestTitle.setVerticalScrollBarEnabled(false); //垂直不显示
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
        if (item.testType == 3){
            container.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        }else {
            container.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        }

        tvCommitTest.setOnClickListener(this);
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
            answerOptionsAdapter = new VideoTestAnswerOptionsAdapter(getAnswerOptions(item));
            answerOptionsAdapter.setOnSelectAnswerListener(this);
            answerOptionsAdapter.setOnTextChangedListener(this);
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
               fillBlankAnswers = new String[item.answer.split("`").length];
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
               answers.add(temp);
           }
           if (!TextUtils.isEmpty(item.questionB)) {
               temp = new VideoTestAnswerOptionEntity(2,item.testType, "B", item.questionB);
               temp.userAnswer = item.userAnswer;
               answers.add(temp);
           }
           if (!TextUtils.isEmpty(item.questionC)) {
               temp = new VideoTestAnswerOptionEntity(3,item.testType, "C", item.questionC);
               temp.userAnswer = item.userAnswer;
               answers.add(temp);
           }
           if (!TextUtils.isEmpty(item.questionD)) {
               temp = new VideoTestAnswerOptionEntity(4,item.testType, "D", item.questionD);
               temp.userAnswer = item.userAnswer;
               answers.add(temp);
           }
           if (!TextUtils.isEmpty(item.questionE)) {
               temp = new VideoTestAnswerOptionEntity(5,item.testType, "E", item.questionE);
               temp.userAnswer = item.userAnswer;
               answers.add(temp);
           }
           if (!TextUtils.isEmpty(item.questionF)) {
               temp = new VideoTestAnswerOptionEntity(6,item.testType, "F", item.questionF);
               temp.userAnswer = item.userAnswer;
               answers.add(temp);
           }
           if (!TextUtils.isEmpty(item.questionG)) {
               temp = new VideoTestAnswerOptionEntity(7,item.testType, "G", item.questionG);
               temp.userAnswer = item.userAnswer;
               answers.add(temp);
           }
           if (!TextUtils.isEmpty(item.questionH)) {
               temp = new VideoTestAnswerOptionEntity(8,item.testType, "H", item.questionH);
               temp.userAnswer = item.userAnswer;
               answers.add(temp);
           }
           if (!TextUtils.isEmpty(item.questionI)) {
               temp = new VideoTestAnswerOptionEntity(9,item.testType, "I", item.questionI);
               temp.userAnswer = item.userAnswer;
               answers.add(temp);
           }
           if (!TextUtils.isEmpty(item.questionJ)) {
               temp = new VideoTestAnswerOptionEntity(10,item.testType, "J", item.questionJ);
               temp.userAnswer = item.userAnswer;
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

    @Override
    public void onSelect(boolean isRadio,String userAswer) {
        if (!isDetached() && testList != null){
            String historyAnswer = testList.get(currentPosition).userAnswer;
            if (isRadio) {
                historyAnswer = userAswer;
            }else {
                if (historyAnswer == null){
                    historyAnswer = userAswer;
                }else {
                    if (!historyAnswer.contains(userAswer)) {
                        historyAnswer = historyAnswer + userAswer;
                    } else {
                        historyAnswer = historyAnswer.replace(userAswer, "");
                    }
                }
            }
            testList.get(currentPosition).userAnswer = historyAnswer;
            answerOptionsAdapter.setNewData(getAnswerOptions(testList.get(currentPosition)));
        }
    }

    StringBuilder answerBuilder;
    @Override
    public void onTextChanged(int position, String text) {
        if (!isDetached() && testList != null){
             if (fillBlankAnswers != null){
                 fillBlankAnswers[position] = text;
                 answerBuilder = new StringBuilder();
                 for (int i = 0;i < fillBlankAnswers.length;i++){
                     answerBuilder.append(TextUtils.isEmpty(fillBlankAnswers[i]) ? " ":fillBlankAnswers[i]);
                     if (i < fillBlankAnswers.length - 1){
                         answerBuilder.append("`");
                     }
                 }
                 testList.get(currentPosition).userAnswer = answerBuilder.toString();
             }
        }
    }

}
