/*
 * Copyright (C) 2024 The Android Open Source Project
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

package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.app.settings.SettingsEnums;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.dashboard.DashboardFragment;

class SourcePresentState extends AudioStreamStateHandler {
    @VisibleForTesting
    static final int AUDIO_STREAM_SOURCE_PRESENT_STATE_SUMMARY = R.string.audio_streams_present_now;

    @Nullable private static SourcePresentState sInstance = null;

    SourcePresentState() {}

    static SourcePresentState getInstance() {
        if (sInstance == null) {
            sInstance = new SourcePresentState();
        }
        return sInstance;
    }

    @Override
    void performAction(
            AudioStreamPreference preference,
            AudioStreamsProgressCategoryController controller,
            AudioStreamsHelper helper) {
        // nothing to do
    }

    @Override
    int getSummary() {
        return AUDIO_STREAM_SOURCE_PRESENT_STATE_SUMMARY;
    }

    @Override
    Preference.OnPreferenceClickListener getOnClickListener(
            AudioStreamsProgressCategoryController controller) {
        return preference -> {
            var p = (AudioStreamPreference) preference;
            Bundle broadcast = new Bundle();
            broadcast.putString(
                    AudioStreamDetailsFragment.BROADCAST_NAME_ARG, (String) p.getTitle());
            broadcast.putInt(
                    AudioStreamDetailsFragment.BROADCAST_ID_ARG, p.getAudioStreamBroadcastId());

            new SubSettingLauncher(p.getContext())
                    .setTitleRes(R.string.audio_streams_detail_page_title)
                    .setDestination(AudioStreamDetailsFragment.class.getName())
                    .setSourceMetricsCategory(
                            !(controller.getFragment() instanceof DashboardFragment)
                                    ? SettingsEnums.PAGE_UNKNOWN
                                    : ((DashboardFragment) controller.getFragment())
                                            .getMetricsCategory())
                    .setArguments(broadcast)
                    .launch();
            return true;
        };
    }

    @Override
    AudioStreamsProgressCategoryController.AudioStreamState getStateEnum() {
        return AudioStreamsProgressCategoryController.AudioStreamState.SOURCE_PRESENT;
    }
}
