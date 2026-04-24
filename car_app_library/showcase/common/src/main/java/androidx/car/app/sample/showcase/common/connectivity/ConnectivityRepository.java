package androidx.car.app.sample.showcase.common.connectivity;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Provides deterministic demo data for the in-vehicle connectivity showcase. */
public final class ConnectivityRepository {
    private static final List<Scenario> SCENARIOS = Arrays.asList(
            new Scenario(
                    "Nominal Drive",
                    "All core links are healthy and media playback is active.",
                    createConnections(
                            connection("Bluetooth", "Connected", "Pixel 8 Pro",
                                    "Hands-free profile active", "16:08"),
                            connection("Wi-Fi", "Connected", "Garage Hotspot",
                                    "Signal stable at -54 dBm", "16:08"),
                            connection("USB", "Mounted", "Sandisk 128GB",
                                    "Media index synced", "16:05"),
                            connection("Phone Projection", "Ready", "Android Auto",
                                    "Projection can be launched", "16:07")),
                    media("Night Drive Playlist", "Bluetooth", "Playing", 18,
                            "Audio routed to cabin speakers"),
                    createEvents(
                            event("16:08", "BT", "CONNECTED",
                                    "Pixel 8 Pro resumed after ignition on"),
                            event("16:07", "Projection", "READY",
                                    "Android Auto handshake completed"),
                            event("16:05", "USB", "MOUNTED",
                                    "Indexed 246 audio files from removable storage")),
                    reconnect("Stable", "No action required", "Monitor only",
                            "0 retries in current drive cycle")),
            new Scenario(
                    "Projection Recovery",
                    "Phone projection dropped once and the service recovered automatically.",
                    createConnections(
                            connection("Bluetooth", "Connected", "Pixel 8 Pro",
                                    "Audio and call profiles restored", "16:12"),
                            connection("Wi-Fi", "Weak Signal", "Garage Hotspot",
                                    "Fallback to cached route tiles", "16:12"),
                            connection("USB", "Mounted", "Sandisk 128GB",
                                    "Playback source remains available", "16:05"),
                            connection("Phone Projection", "Reconnecting", "Android Auto",
                                    "Retry 2/3 in progress", "16:12")),
                    media("Navigation Prompt", "USB", "Buffering", 16,
                            "Waiting for projection audio focus"),
                    createEvents(
                            event("16:12", "Projection", "LOST",
                                    "Projection session lost after Wi-Fi fluctuation"),
                            event("16:12", "Projection", "RETRY",
                                    "Reconnect attempt 2 of 3 started"),
                            event("16:11", "Wi-Fi", "DEGRADED",
                                    "RSSI dropped below reconnect threshold")),
                    reconnect("Recovering", "Projection reconnect in progress", "Retry 2 of 3",
                            "User-facing audio kept on USB until link restoration")),
            new Scenario(
                    "Timeout Analysis",
                    "The Bluetooth accessory did not return before the timeout threshold.",
                    createConnections(
                            connection("Bluetooth", "Disconnected", "Driver Headset",
                                    "Last seen 48 seconds ago", "16:15"),
                            connection("Wi-Fi", "Connected", "Vehicle AP",
                                    "Backhaul remains available", "16:15"),
                            connection("USB", "Unplugged", "No media drive",
                                    "Port idle", "16:10"),
                            connection("Phone Projection", "Unavailable", "No device",
                                    "Waiting for eligible phone", "16:15")),
                    media("No Active Playback", "Local Cache", "Paused", 0,
                            "Playback stopped after source removal"),
                    createEvents(
                            event("16:15", "BT", "TIMEOUT",
                                    "Reconnect window expired after three retries"),
                            event("16:14", "BT", "RETRY",
                                    "Retry 3 failed with no response"),
                            event("16:10", "USB", "REMOVED",
                                    "Storage detached during playback transition")),
                    reconnect("Attention Needed", "Bluetooth recovery failed",
                            "Escalate to user",
                            "Suggest manual re-pair or accessory power cycle")));

    private static int sScenarioIndex;

    private ConnectivityRepository() {
    }

    @NonNull
    public static Scenario currentScenario() {
        return SCENARIOS.get(sScenarioIndex);
    }

    public static void advanceScenario() {
        sScenarioIndex = (sScenarioIndex + 1) % SCENARIOS.size();
    }

    public static void resetScenario() {
        sScenarioIndex = 0;
    }

    private static List<ConnectionItem> createConnections(ConnectionItem... items) {
        return Arrays.asList(items);
    }

    private static List<EventLogItem> createEvents(EventLogItem... items) {
        return Arrays.asList(items);
    }

    @NonNull
    private static ConnectionItem connection(
            @NonNull String type,
            @NonNull String status,
            @NonNull String deviceName,
            @NonNull String detail,
            @NonNull String updatedAt) {
        return new ConnectionItem(type, status, deviceName, detail, updatedAt);
    }

    @NonNull
    private static MediaState media(
            @NonNull String title,
            @NonNull String source,
            @NonNull String playbackState,
            int volumeLevel,
            @NonNull String detail) {
        return new MediaState(title, source, playbackState, volumeLevel, detail);
    }

    @NonNull
    private static EventLogItem event(
            @NonNull String timestamp,
            @NonNull String module,
            @NonNull String eventType,
            @NonNull String message) {
        return new EventLogItem(timestamp, module, eventType, message);
    }

    @NonNull
    private static ReconnectState reconnect(
            @NonNull String status,
            @NonNull String summary,
            @NonNull String nextAction,
            @NonNull String detail) {
        return new ReconnectState(status, summary, nextAction, detail);
    }

    public static final class Scenario {
        @NonNull public final String name;
        @NonNull public final String summary;
        @NonNull public final List<ConnectionItem> connections;
        @NonNull public final MediaState mediaState;
        @NonNull public final List<EventLogItem> eventLog;
        @NonNull public final ReconnectState reconnectState;

        Scenario(
                @NonNull String name,
                @NonNull String summary,
                @NonNull List<ConnectionItem> connections,
                @NonNull MediaState mediaState,
                @NonNull List<EventLogItem> eventLog,
                @NonNull ReconnectState reconnectState) {
            this.name = name;
            this.summary = summary;
            this.connections = new ArrayList<>(connections);
            this.mediaState = mediaState;
            this.eventLog = new ArrayList<>(eventLog);
            this.reconnectState = reconnectState;
        }
    }

    public static final class ConnectionItem {
        @NonNull public final String type;
        @NonNull public final String status;
        @NonNull public final String deviceName;
        @NonNull public final String detail;
        @NonNull public final String updatedAt;

        ConnectionItem(
                @NonNull String type,
                @NonNull String status,
                @NonNull String deviceName,
                @NonNull String detail,
                @NonNull String updatedAt) {
            this.type = type;
            this.status = status;
            this.deviceName = deviceName;
            this.detail = detail;
            this.updatedAt = updatedAt;
        }
    }

    public static final class MediaState {
        @NonNull public final String title;
        @NonNull public final String source;
        @NonNull public final String playbackState;
        public final int volumeLevel;
        @NonNull public final String detail;

        MediaState(
                @NonNull String title,
                @NonNull String source,
                @NonNull String playbackState,
                int volumeLevel,
                @NonNull String detail) {
            this.title = title;
            this.source = source;
            this.playbackState = playbackState;
            this.volumeLevel = volumeLevel;
            this.detail = detail;
        }
    }

    public static final class EventLogItem {
        @NonNull public final String timestamp;
        @NonNull public final String module;
        @NonNull public final String eventType;
        @NonNull public final String message;

        EventLogItem(
                @NonNull String timestamp,
                @NonNull String module,
                @NonNull String eventType,
                @NonNull String message) {
            this.timestamp = timestamp;
            this.module = module;
            this.eventType = eventType;
            this.message = message;
        }
    }

    public static final class ReconnectState {
        @NonNull public final String status;
        @NonNull public final String summary;
        @NonNull public final String nextAction;
        @NonNull public final String detail;

        ReconnectState(
                @NonNull String status,
                @NonNull String summary,
                @NonNull String nextAction,
                @NonNull String detail) {
            this.status = status;
            this.summary = summary;
            this.nextAction = nextAction;
            this.detail = detail;
        }
    }
}
