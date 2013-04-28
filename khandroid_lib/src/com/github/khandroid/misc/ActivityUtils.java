/*
 * Copyright (C) 2012-2013 Ognyan Bankov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.khandroid.misc;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;


public class ActivityUtils {
    public static final Button findButton(View view, int resourceId) {
        Button ret = null;

        ret = (Button) view.findViewById(resourceId);
        if (ret == null) {
            KhandroidLog.w("Cannot find Button with id = " + resourceId);
        }

        return ret;
    }


    public static final Button findButtonX(View view, int resourceId) {
        Button ret = null;

        ret = (Button) view.findViewById(resourceId);
        if (ret == null) {
            throw new RuntimeException("Cannot find Button with id = " + resourceId);
        }

        return ret;
    }


    public static final Button initButton(View view, int resourceId, View.OnClickListener listener) {
        Button ret = null;

        ret = findButtonX(view, resourceId);
        ret.setOnClickListener(listener);

        return ret;
    }


    public static final TextView findTextView(View view, int resourceId) {
        TextView ret = null;

        ret = (TextView) view.findViewById(resourceId);
        if (ret == null) {
            KhandroidLog.w("Cannot find TextView with id = " + resourceId);
        }

        return ret;
    }


    public static final TextView findTextViewX(View view, int resourceId) {
        TextView ret = null;

        ret = (TextView) view.findViewById(resourceId);
        if (ret == null) {
            throw new RuntimeException("Cannot find TextView with id = " + resourceId);
        }

        return ret;
    }


    public static final EditText findEditText(View view, int resourceId) {
        EditText ret = null;

        ret = (EditText) view.findViewById(resourceId);
        if (ret == null) {
            KhandroidLog.w("Cannot find EditText with id = " + resourceId);
        }

        return ret;
    }


    public static final EditText findEditTextX(View view, int resourceId) {
        EditText ret = null;

        ret = (EditText) view.findViewById(resourceId);
        if (ret == null) {
            throw new RuntimeException("Cannot find EditText with id = " + resourceId);
        }

        return ret;
    }


    public static final EditText initEditText(View view, int resourceId, TextWatcher watcher) {
        EditText ret = null;

        ret = findEditTextX(view, resourceId);
        ret.addTextChangedListener(watcher);

        return ret;
    }


    public static final RadioGroup findRadioGroup(View view, int resourceId) {
        RadioGroup ret = null;

        ret = (RadioGroup) view.findViewById(resourceId);
        if (ret == null) {
            KhandroidLog.w("Cannot find RadioGroup with id = " + resourceId);
        }

        return ret;
    }


    public static final RadioGroup findRadioGroupX(View view, int resourceId) {
        RadioGroup ret = null;

        ret = (RadioGroup) view.findViewById(resourceId);
        if (ret == null) {
            throw new RuntimeException("Cannot find RadioGroup with id = " + resourceId);
        }

        return ret;
    }


    public static final RadioGroup initRadioGroup(View view,
                                                  int resourceId,
                                                  RadioGroup.OnCheckedChangeListener listener) {
        RadioGroup ret = null;

        ret = findRadioGroupX(view, resourceId);
        ret.setOnCheckedChangeListener(listener);

        return ret;
    }


    public static final CheckBox findCheckBox(View view, int resourceId) {
        CheckBox ret = null;

        ret = (CheckBox) view.findViewById(resourceId);
        if (ret == null) {
            KhandroidLog.w("Cannot find CheckBox with id = " + resourceId);
        }

        return ret;
    }


    public static final CheckBox findCheckBoxX(View view, int resourceId) {
        CheckBox ret = null;

        ret = (CheckBox) view.findViewById(resourceId);
        if (ret == null) {
            throw new RuntimeException("Cannot find CheckBox with id = " + resourceId);
        }

        return ret;
    }


    public static final CheckBox initCheckBox(View view,
                                              int resourceId,
                                              CompoundButton.OnCheckedChangeListener listener) {
        CheckBox ret = null;

        ret = findCheckBoxX(view, resourceId);
        ret.setOnCheckedChangeListener(listener);

        return ret;
    }


    public static final ToggleButton findToggleButton(View view, int resourceId) {
        ToggleButton ret = null;

        ret = (ToggleButton) view.findViewById(resourceId);
        if (ret == null) {
            KhandroidLog.w("Cannot find ToggleButton with id = " + resourceId);
        }

        return ret;
    }


    public static final ToggleButton findToggleButtonX(View view, int resourceId) {
        ToggleButton ret = null;

        ret = (ToggleButton) view.findViewById(resourceId);
        if (ret == null) {
            throw new RuntimeException("Cannot find ToggleButton with id = " + resourceId);
        }

        return ret;
    }


    public static final ToggleButton initToggleButton(View view,
                                                      int resourceId,
                                                      CompoundButton.OnCheckedChangeListener listener) {
        ToggleButton ret = null;

        ret = findToggleButtonX(view, resourceId);
        ret.setOnCheckedChangeListener(listener);

        return ret;
    }


    public static long interceptLongParam(Bundle savedInstanceState,
                                          Intent intent, String paramName) {
        long ret = 0;

        if (savedInstanceState != null) {
            ret = savedInstanceState.getLong(paramName);
        } else {
            if (intent != null) {
                Bundle incomming = intent.getExtras();
                if (incomming != null) {
                    ret = incomming.getLong(paramName);
                }
            }
        }

        return ret;
    }


    public static Parcelable interceptParcelableParam(
                                                      Bundle savedInstanceState,
                                                      Intent intent,
                                                      String paramName) {
        Parcelable ret = null;

        if (savedInstanceState != null) {
            ret = savedInstanceState.getParcelable(paramName);
        } else {
            if (intent != null) {
                Bundle incomming = intent.getExtras();
                if (incomming != null) {
                    ret = incomming.getParcelable(paramName);
                }
            }
        }

        return ret;
    }


    public static String interceptStringParam(Bundle savedInstanceState,
                                              Intent intent, String paramName) {
        String ret = null;

        if (savedInstanceState != null) {
            ret = savedInstanceState.getString(paramName);
        } else {
            if (intent != null) {
                Bundle incomming = intent.getExtras();
                if (incomming != null) {
                    ret = incomming.getString(paramName);
                }
            }
        }

        return ret;
    }


    public static boolean interceptBooleanParam(Bundle savedInstanceState,
                                                Intent intent, String paramName) {
        boolean ret = false;

        if (savedInstanceState != null) {
            ret = savedInstanceState.getBoolean(paramName, false);
        } else {
            if (intent != null) {
                Bundle incomming = intent.getExtras();
                if (incomming != null) {
                    ret = incomming.getBoolean(paramName, false);
                }
            }
        }

        return ret;
    }


    public static int interceptIntParam(Bundle savedInstanceState,
                                        Intent intent, String paramName) {
        int ret = -1;

        if (savedInstanceState != null) {
            ret = savedInstanceState.getInt(paramName, -1);
        } else {
            if (intent != null) {
                Bundle incomming = intent.getExtras();
                if (incomming != null) {
                    ret = incomming.getInt(paramName, -1);
                }
            }
        }

        return ret;
    }


    public static float interceptFloatParam(Bundle savedInstanceState,
                                            Intent intent, String paramName) {
        float ret = 0;

        if (savedInstanceState != null) {
            ret = savedInstanceState.getFloat(paramName, 0);
        } else {
            if (intent != null) {
                Bundle incomming = intent.getExtras();
                if (incomming != null) {
                    ret = incomming.getFloat(paramName, 0);
                }
            }
        }

        return ret;
    }


    public static double interceptDoubleParam(Bundle savedInstanceState,
                                              Intent intent, String paramName) {
        double ret = 0;

        if (savedInstanceState != null) {
            ret = savedInstanceState.getDouble(paramName, 0);
        } else {
            if (intent != null) {
                Bundle incomming = intent.getExtras();
                if (incomming != null) {
                    ret = incomming.getDouble(paramName, 0);
                }
            }
        }

        return ret;
    }


    public static byte interceptByteParam(Bundle savedInstanceState,
                                          Intent intent, String paramName) {
        byte ret = 0;

        if (savedInstanceState != null) {
            ret = savedInstanceState.getByte(paramName, (byte) 0);
        } else {
            if (intent != null) {
                Bundle incomming = intent.getExtras();
                if (incomming != null) {
                    ret = incomming.getByte(paramName, (byte) 0);
                }
            }
        }

        return ret;
    }


    public static char interceptCharParam(Bundle savedInstanceState,
                                          Intent intent, String paramName) {
        char ret = 0;

        if (savedInstanceState != null) {
            ret = savedInstanceState.getChar(paramName, (char) 0);
        } else {
            if (intent != null) {
                Bundle incomming = intent.getExtras();
                if (incomming != null) {
                    ret = incomming.getChar(paramName, (char) 0);
                }
            }
        }

        return ret;
    }


    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }

        return false;
    }
}
