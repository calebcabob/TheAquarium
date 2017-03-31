/*
 * Copyright (C) 2013 The Android Open-Source Project
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

package com.aquarios.settings.fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.os.UserHandle;
import android.preference.ListPreference;
import android.support.v7.preference.PreferenceScreen;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings;
import android.view.View;
import net.margaritov.preference.colorpicker.ColorPickerPreference;
import android.support.v4.app.Fragment;
import android.widget.LinearLayout;

import com.android.internal.logging.MetricsProto.MetricsEvent;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class StatusBarSettings extends SettingsPreferenceFragment {

     private static final String PREF_STATUS_BAR_WEATHER = "status_bar_weather";

     private ListPreference mStatusBarWeather;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.statusbar_settings);
        final PreferenceScreen prefScreen = getPreferenceScreen();

       // Status bar weather
       mStatusBarWeather = (ListPreference) findPreference(PREF_STATUS_BAR_WEATHER);
       int temperatureShow = Settings.System.getIntForUser(resolver,
               Settings.System.STATUS_BAR_SHOW_WEATHER_TEMP, 0,
               UserHandle.USER_CURRENT);
       mStatusBarWeather.setValue(String.valueOf(temperatureShow));
       if (temperatureShow == 0) {
           mStatusBarWeather.setSummary(R.string.statusbar_weather_summary);
       } else {
           mStatusBarWeather.setSummary(mStatusBarWeather.getEntry());
       }
          mStatusBarWeather.setOnPreferenceChangeListener(this);
    }

    @Override
    protected int getMetricsCategory() {
        return MetricsEvent.AQUARIOS;
    }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            ContentResolver resolver = getActivity().getContentResolver()
            if (preference == mStatusBarWeather) {
                int temperatureShow = Integer.valueOf((String) newValue);
                int index = mStatusBarWeather.findIndexOfValue((String) newValue);
                Settings.System.putIntForUser(resolver,
                        Settings.System.STATUS_BAR_SHOW_WEATHER_TEMP,
                        temperatureShow, UserHandle.USER_CURRENT);
                if (temperatureShow == 0) {
                    mStatusBarWeather.setSummary(R.string.statusbar_weather_summary);
                } else {
                    mStatusBarWeather.setSummary(
                            mStatusBarWeather.getEntries()[index]);
                }
                return true;
           }
           return false;
     }
}
