package androidx.car.app.sample.showcase;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.car.app.sample.showcase.common.connectivity.ConnectivityRepository;

/** Phone-friendly demo entry point for showcasing connectivity scenarios. */
public final class MainActivity extends Activity {
    private TextView mScenarioTitle;
    private TextView mScenarioSummary;
    private TextView mConnections;
    private TextView mMedia;
    private TextView mEventLog;
    private TextView mReconnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScenarioTitle = findViewById(R.id.scenario_title);
        mScenarioSummary = findViewById(R.id.scenario_summary);
        mConnections = findViewById(R.id.connections_body);
        mMedia = findViewById(R.id.media_body);
        mEventLog = findViewById(R.id.event_log_body);
        mReconnect = findViewById(R.id.reconnect_body);

        Button cycleScenario = findViewById(R.id.cycle_scenario_button);
        cycleScenario.setOnClickListener(view -> {
            ConnectivityRepository.advanceScenario();
            renderScenario();
        });

        renderScenario();
    }

    private void renderScenario() {
        ConnectivityRepository.Scenario scenario = ConnectivityRepository.currentScenario();
        mScenarioTitle.setText(scenario.name);
        mScenarioSummary.setText(scenario.summary);
        mConnections.setText(buildConnectionsText(scenario));
        mMedia.setText(buildMediaText(scenario));
        mEventLog.setText(buildEventLogText(scenario));
        mReconnect.setText(buildReconnectText(scenario));
    }

    private String buildConnectionsText(ConnectivityRepository.Scenario scenario) {
        StringBuilder builder = new StringBuilder();
        for (ConnectivityRepository.ConnectionItem item : scenario.connections) {
            if (builder.length() > 0) {
                builder.append("\n\n");
            }
            builder.append(item.type)
                    .append(": ")
                    .append(item.status)
                    .append("\n")
                    .append(item.deviceName)
                    .append("\n")
                    .append(item.detail)
                    .append(" | ")
                    .append(item.updatedAt);
        }
        return builder.toString();
    }

    private String buildMediaText(ConnectivityRepository.Scenario scenario) {
        ConnectivityRepository.MediaState mediaState = scenario.mediaState;
        return getString(
                R.string.mobile_media_format,
                mediaState.title,
                mediaState.source,
                mediaState.playbackState,
                mediaState.volumeLevel,
                mediaState.detail);
    }

    private String buildEventLogText(ConnectivityRepository.Scenario scenario) {
        StringBuilder builder = new StringBuilder();
        for (ConnectivityRepository.EventLogItem item : scenario.eventLog) {
            if (builder.length() > 0) {
                builder.append("\n\n");
            }
            builder.append(item.timestamp)
                    .append("  ")
                    .append(item.module)
                    .append("  ")
                    .append(item.eventType)
                    .append("\n")
                    .append(item.message);
        }
        return builder.toString();
    }

    private String buildReconnectText(ConnectivityRepository.Scenario scenario) {
        ConnectivityRepository.ReconnectState reconnectState = scenario.reconnectState;
        return getString(
                R.string.mobile_reconnect_format,
                reconnectState.status,
                reconnectState.summary,
                reconnectState.nextAction,
                reconnectState.detail);
    }
}
