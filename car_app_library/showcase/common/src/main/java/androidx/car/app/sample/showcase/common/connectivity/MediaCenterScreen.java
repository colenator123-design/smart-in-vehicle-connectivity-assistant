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

/** Highlights infotainment media state for the demo vehicle. */
public final class MediaCenterScreen extends Screen {
    public MediaCenterScreen(@NonNull CarContext carContext) {
        super(carContext);
    }

    @NonNull
    @Override
    public Template onGetTemplate() {
        ConnectivityRepository.MediaState mediaState =
                ConnectivityRepository.currentScenario().mediaState;

        Pane.Builder paneBuilder = new Pane.Builder()
                .addRow(new Row.Builder()
                        .setTitle(getCarContext().getString(R.string.media_now_playing_title))
                        .addText(mediaState.title)
                        .build())
                .addRow(new Row.Builder()
                        .setTitle(getCarContext().getString(R.string.media_source_label))
                        .addText(mediaState.source)
                        .build())
                .addRow(new Row.Builder()
                        .setTitle(getCarContext().getString(R.string.media_playback_state_label))
                        .addText(mediaState.playbackState)
                        .addText(mediaState.detail)
                        .build())
                .addRow(new Row.Builder()
                        .setTitle(getCarContext().getString(R.string.media_volume_label))
                        .addText(getCarContext().getString(
                                R.string.media_volume_value,
                                mediaState.volumeLevel))
                        .build());

        return new PaneTemplate.Builder(paneBuilder.build())
                .setTitle(getCarContext().getString(R.string.media_center_title))
                .setHeaderAction(Action.BACK)
                .setActionStrip(new ActionStrip.Builder()
                        .addAction(new Action.Builder()
                                .setTitle(getCarContext().getString(
                                        R.string.media_toggle_action_title))
                                .setOnClickListener(() -> {
                                    ConnectivityRepository.advanceScenario();
                                    invalidate();
                                })
                                .build())
                        .build())
                .build();
    }
}
