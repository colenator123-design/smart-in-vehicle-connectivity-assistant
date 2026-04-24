package androidx.car.app.sample.showcase.common.connectivity;

import androidx.annotation.NonNull;
import androidx.car.app.CarContext;
import androidx.car.app.Screen;
import androidx.car.app.model.Action;
import androidx.car.app.model.ItemList;
import androidx.car.app.model.ListTemplate;
import androidx.car.app.model.Row;
import androidx.car.app.model.Template;
import androidx.car.app.sample.showcase.common.R;

/** Displays recent simulated system events for debugging and issue analysis. */
public final class EventLogScreen extends Screen {
    public EventLogScreen(@NonNull CarContext carContext) {
        super(carContext);
    }

    @NonNull
    @Override
    public Template onGetTemplate() {
        ItemList.Builder listBuilder = new ItemList.Builder();

        for (ConnectivityRepository.EventLogItem item
                : ConnectivityRepository.currentScenario().eventLog) {
            listBuilder.addItem(new Row.Builder()
                    .setTitle(item.timestamp + "  " + item.module + "  " + item.eventType)
                    .addText(item.message)
                    .build());
        }

        return new ListTemplate.Builder()
                .setSingleList(listBuilder.build())
                .setTitle(getCarContext().getString(R.string.event_log_title))
                .setHeaderAction(Action.BACK)
                .build();
    }
}
