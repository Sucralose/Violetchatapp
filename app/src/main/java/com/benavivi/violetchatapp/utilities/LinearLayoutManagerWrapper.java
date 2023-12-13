package com.benavivi.violetchatapp.utilities;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * This wrapper is used to prevent the recyclerview from outputting
 * 'java.lang.IndexOutOfBoundsException: Inconsistency detected. Invalid view holder' error when switching quickly
 * between ConversationActivity and ChatsFragment. This is done by disabling the predictive animation.
 * <p>
 * See about this issue and it's solutions at https://stackoverflow.com/a/54090759
 */

public class LinearLayoutManagerWrapper extends LinearLayoutManager {


public LinearLayoutManagerWrapper ( Context context ) {
	super(context);
}

public LinearLayoutManagerWrapper ( Context context, int orientation, boolean reverseLayout ) {
	super(context, orientation, reverseLayout);
}

public LinearLayoutManagerWrapper ( Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes ) {
	super(context, attrs, defStyleAttr, defStyleRes);
}

@Override
public boolean supportsPredictiveItemAnimations ( ) {
	return false;
}
}
