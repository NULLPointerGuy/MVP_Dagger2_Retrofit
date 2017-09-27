package com.karthik.corecommon.Views;

import android.os.Bundle;

import com.firebase.jobdispatcher.Job;

/**
 * Created by karthikr on 8/8/17.
 */

public interface AddTodoView {
    boolean isTodoValidTitle();
    void showAppropriateError();
    String getTodoTitle();
    void showSaveSuccessMessage();
}
