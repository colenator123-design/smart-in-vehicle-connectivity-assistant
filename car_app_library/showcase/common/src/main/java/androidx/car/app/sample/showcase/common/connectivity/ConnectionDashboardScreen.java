package androidx.car.app.sample.showcase.common.connectivity;

import androidx.annotation.NonNull;
import androidx.car.app.CarContext;
import androidx.car.app.Screen;
import androidx.car.app.model.Action;
import androidx.car.app.model.ActionStrip;
import androidx.car.app.model.ItemList;
import androidx.car.app.model.ListTemplate;
import androidx.car.app.model.Row;
import androidx.car.app.model.Template;
import androidx.car.app.sample.showcase.common.R;

/** Shows the current simulated link status for in-vehicle connectivity. */
public final class ConnectionDashboardScreen extends Screen {
    public ConnectionDashboardScreen(@NonNull CarContext carContext) {
        super(carContext);
    }

    @NonNull
    @Override
    public Template onGetTemplate() {
        ConnectivityRepository.Scenario scenario = ConnectivityRepository.currentScenario();
        ItemList.Builder listBuilder = new ItemList.Builder();

        for (ConnectivityRepository.ConnectionItem item : scenario.connections) {
            listBuilder.addItem(new Row.Builder()
                    .setTitle(item.type + ": " + item.status)
                    .addText(item.deviceName)
                    .addText(item.detail + " | " + item.updatedAt)
                    .build());
        }

        return new ListTemplate.Builder()
                .setSingleList(listBuilder.build())
                .setTitle(getCarContext().getString(R.string.connectivity_overview_title))
                .setHeaderAction(Action.BACK)
                .setActionStrip(new ActionStrip.Builder()
                        .addAction(new Action.Builder()
                                .setTitle(getCarContext().getString(
                                        R.string.refresh_action_title))
                                .setOnClickListener(() -> {
                                    ConnectivityRepository.advanceScenario();
                                    invalidate();
                                })
                                .build())
                        .build())
                .build();
    }
}
