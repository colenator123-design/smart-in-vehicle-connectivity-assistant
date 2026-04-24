package androidx.car.app.sample.showcase.common.connectivity;

import androidx.annotation.NonNull;
import androidx.car.app.CarContext;
import androidx.car.app.Screen;
import androidx.car.app.model.Action;
import androidx.car.app.model.ActionStrip;
import androidx.car.app.model.Pane;
import androidx.car.app.model.PaneTemplate;
import androidx.car.app.model.Row;
import androidx.car.app.model.Template;
import androidx.car.app.sample.showcase.common.R;

/** Summarizes recovery behavior after a connectivity interruption. */
public final class ReconnectFlowScreen extends Screen {
    public ReconnectFlowScreen(@NonNull CarContext carContext) {
        super(carContext);
    }

    @NonNull
    @Override
    public Template onGetTemplate() {
        ConnectivityRepository.ReconnectState reconnectState =
                ConnectivityRepository.currentScenario().reconnectState;

        Pane.Builder paneBuilder = new Pane.Builder()
                .addRow(new Row.Builder()
                        .setTitle(getCarContext().getString(R.string.reconnect_status_label))
                        .addText(reconnectState.status)
                        .build())
                .addRow(new Row.Builder()
                        .setTitle(getCarContext().getString(R.string.reconnect_summary_label))
                        .addText(reconnectState.summary)
                        .addText(reconnectState.detail)
                        .build())
                .addRow(new Row.Builder()
                        .setTitle(getCarContext().getString(R.string.reconnect_next_action_label))
                        .addText(reconnectState.nextAction)
                        .build());

        return new PaneTemplate.Builder(paneBuilder.build())
                .setTitle(getCarContext().getString(R.string.reconnect_flow_title))
                .setHeaderAction(Action.BACK)
                .setActionStrip(new ActionStrip.Builder()
                        .addAction(new Action.Builder()
                                .setTitle(getCarContext().getString(
                                        R.string.cycle_scenario_action_title))
                                .setOnClickListener(() -> {
                                    ConnectivityRepository.advanceScenario();
                                    invalidate();
                                })
                                .build())
                        .build())
                .build();
    }
}
