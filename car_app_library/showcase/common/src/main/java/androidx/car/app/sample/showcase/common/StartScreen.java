/*
 * Copyright (C) 2021 The Android Open Source Project
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
package androidx.car.app.sample.showcase.common;

import androidx.annotation.NonNull;
import androidx.car.app.CarContext;
import androidx.car.app.Screen;
import androidx.car.app.model.Action;
import androidx.car.app.model.ActionStrip;
import androidx.car.app.model.ItemList;
import androidx.car.app.model.ListTemplate;
import androidx.car.app.model.Row;
import androidx.car.app.model.Template;
import androidx.car.app.sample.showcase.common.connectivity.ConnectionDashboardScreen;
import androidx.car.app.sample.showcase.common.connectivity.ConnectivityRepository;
import androidx.car.app.sample.showcase.common.connectivity.EventLogScreen;
import androidx.car.app.sample.showcase.common.connectivity.MediaCenterScreen;
import androidx.car.app.sample.showcase.common.connectivity.ReconnectFlowScreen;

/** The starting screen of the app. */
public final class StartScreen extends Screen {
    public StartScreen(@NonNull CarContext carContext, @NonNull ShowcaseSession showcaseSession) {
        super(carContext);
    }

    @NonNull
    @Override
    public Template onGetTemplate() {
        ConnectivityRepository.Scenario scenario = ConnectivityRepository.currentScenario();
        ItemList.Builder listBuilder = new ItemList.Builder();

        listBuilder.addItem(new Row.Builder()
                .setTitle(getCarContext().getString(R.string.connectivity_overview_title))
                .addText(scenario.name)
                .addText(scenario.summary)
                .setBrowsable(true)
                .setOnClickListener(() -> getScreenManager().push(
                        new ConnectionDashboardScreen(getCarContext())))
                .build());
        listBuilder.addItem(new Row.Builder()
                .setTitle(getCarContext().getString(R.string.media_center_title))
                .addText(scenario.mediaState.playbackState + " via " + scenario.mediaState.source)
                .addText(scenario.mediaState.title)
                .setBrowsable(true)
                .setOnClickListener(() -> getScreenManager().push(
                        new MediaCenterScreen(getCarContext())))
                .build());
        listBuilder.addItem(new Row.Builder()
                .setTitle(getCarContext().getString(R.string.event_log_title))
                .addText(getCarContext().getString(
                        R.string.event_log_summary,
                        scenario.eventLog.size(),
                        scenario.eventLog.get(0).eventType))
                .setBrowsable(true)
                .setOnClickListener(() -> getScreenManager().push(
                        new EventLogScreen(getCarContext())))
                .build());
        listBuilder.addItem(new Row.Builder()
                .setTitle(getCarContext().getString(R.string.reconnect_flow_title))
                .addText(scenario.reconnectState.status)
                .addText(scenario.reconnectState.summary)
                .setBrowsable(true)
                .setOnClickListener(() -> getScreenManager().push(
                        new ReconnectFlowScreen(getCarContext())))
                .build());

        Action cycleScenario = new Action.Builder()
                .setTitle(getCarContext().getString(R.string.cycle_scenario_action_title))
                .setOnClickListener(() -> {
                    ConnectivityRepository.advanceScenario();
                    invalidate();
                })
                .build();

        return new ListTemplate.Builder()
                .setSingleList(listBuilder.build())
                .setTitle(getCarContext().getString(R.string.app_name))
                .setHeaderAction(Action.APP_ICON)
                .setActionStrip(new ActionStrip.Builder().addAction(cycleScenario).build())
                .build();
    }
}
