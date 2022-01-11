package com.example.demo.generic;

import java.math.RoundingMode;

public final class StaticProperties {

    private StaticProperties() {
    }

    public static final String API_BASE_URI = "/api";

    public static final String TASKS_SCHEDULE_INTERVAL = "${tasks.schedule.interval}";
    public static final String TASKS_SCHEDULE_INTERVAL_1 = "${tasks.schedule.interval1}";
    public static final String TASKS_SCHEDULE_INTERVAL_2 = "${tasks.schedule.interval2}";
    public static final String TASKS_SCHEDULE_INTERVAL_3 = "${tasks.schedule.interval3}";

    public static final String PRICES_DOWNLOAD_SCHEDULE_INTERVAL_1 = "${import.input.prices.schedule.interval1}";
    public static final String PRICES_DOWNLOAD_SCHEDULE_INTERVAL_2 = "${import.input.prices.schedule.interval2}";
    public static final String PRICES_DOWNLOAD_SCHEDULE_INTERVAL_3 = "${import.input.prices.schedule.interval3}";

    public static final String PRICES_PROCESS_SCHEDULE_INTERVAL = "${process.input.prices.schedule_interval}";

    public static final String PRICES_TO_HELIOS_DOWNLOAD_SCHEDULE_INTERVAL_1 = "${import.input.prices.to.helios.schedule.interval1}";
    public static final String PRICES_TO_HELIOS_DOWNLOAD_SCHEDULE_INTERVAL_2 = "${import.input.prices.to.helios.schedule.interval2}";
    public static final String PRICES_TO_HELIOS_DOWNLOAD_SCHEDULE_INTERVAL_3 = "${import.input.prices.to.helios.schedule.interval3}";

    public static final String PRICES_TO_HELIOS_PROCESS_SCHEDULE_INTERVAL = "${process.input.prices.to.helios.schedule_interval}";
    public static final String FX_RATES_SCHEDULE_INTERVAL = "${import.input.fx_rates.schedule_interval}";
    public static final String WMFWD_SCHEDULE_INTERVAL = "${import.input.wmfwd.schedule_interval}";
    public static final String NIKKEI_PARSE_SCHEDULE_INTERVAL = "${import.input.nikkei.parse.schedule_interval}";

    public static final String MCA_C2_DOWNLOAD_SCHEDULE_INTERVAL = "${import.input.mca.c2.schedule_interval}";
    public static final String MCA_C2_PROCESS_SCHEDULE_INTERVAL = "${process.mca.c2.schedule_interval}";
    public static final String MCA_C4_DOWNLOAD_SCHEDULE_INTERVAL = "${import.input.mca.c4.schedule_interval}";
    public static final String MCA_C4_PROCESS_SCHEDULE_INTERVAL = "${process.mca.c4.schedule_interval}";
    public static final String MCA_C13_DOWNLOAD_SCHEDULE_INTERVAL = "${import.input.mca.c13.schedule_interval}";
    public static final String MCA_C13_PROCESS_SCHEDULE_INTERVAL = "${process.mca.c13.schedule_interval}";
    public static final String MCA_SO_ADJUST_SCHEDULE_INTERVAL = "${process.mca.so.adjust.schedule_interval}";
    public static final String TREP_RIC_FILE_SCHEDULE_INTERVAL = "${process.trep.ric.file.schedule_interval}";
    public static final String TICKER_FILE_SCHEDULE_INTERVAL = "${process.ticker.file.schedule_interval}";
    public static final String EQUITIES_EXPORT_FILE_SCHEDULE = "${process.export.equities.file.schedule}";
    public static final String RTE_TICKER_CREATION_SCHEDULE = "${process.rte.tiker.creation.schedule}";
    public static final String SCI_BETA_DOWNLOAD_SCHEDULE_INTERVAL = "${import.input.scibeta.schedule_interval}";

    public static final String CONSTITUENT_MEMBERSHIP_EXPORT_FILE_SCHEDULE = "${process.export.constituent.membership.file.schedule}";

    public static final String DATA_LICENCE_REQUEST_EXPORT_FILE_SCHEDULE_1 = "${process.export.data.licence.request.file.schedule1}";
    public static final String DATA_LICENCE_REQUEST_EXPORT_FILE_SCHEDULE_2 = "${process.export.data.licence.request.file.schedule2}";

    public static final String DATA_LICENCE_RESPONSE_EXPORT_FILE_SCHEDULE_1 = "${process.export.data.licence.response.file.schedule1}";
    public static final String DATA_LICENCE_RESPONSE_EXPORT_FILE_SCHEDULE_2 = "${process.export.data.licence.response.file.schedule2}";
    public static final String DATA_LICENCE_RESPONSE_EXPORT_FILE_SCHEDULE_3 = "${process.export.data.licence.response.file.schedule3}";

    public static final String CUSTOM_FX_DOWNLOAD_SCHEDULE_INTERVAL_1 = "${import.input.custom.fx.schedule.interval1}";
    public static final String CUSTOM_FX_DOWNLOAD_SCHEDULE_INTERVAL_2 = "${import.input.custom.fx.schedule.interval2}";
    public static final String CUSTOM_FX_DOWNLOAD_SCHEDULE_INTERVAL_3 = "${import.input.custom.fx.schedule.interval3}";

    public static final String CROP_CLARK_FILE_DOWNLOAD_SCHEDULE = "${import.input.corpclark.file.download.schedule.interval}";
    public static final String CROP_CLARK_FILE_PROCESS_SCHEDULE = "${process.input.corpclark.file.schedule.interval}";

    public static final String CRON_PRINT_ACTIVE_THREAD_COUNT = "${cron.print.active.thread.count}";


    public static final int EXTRA_LARGE_DIMENSION = 16;
    public static final int LARGE_DIMENSION = 12;
    public static final int MEDIUM_DIMENSION = 10;
    public static final int SMALL_DIMENSION = 6;
    public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    public static final String UTC = "UTC";
    public static final String EUROPE_LONDON = "Europe/London";
    public static final String ASIA_SINGAPORE = "Asia/Singapore";
    public static final String ASIA_TOKYO = "Asia/Tokyo";
    public static final String AMERICA_MEXICO_CITY = "America/Mexico_City";
    public static final String WMR_REPORT_DATE_PATTERN = "'London 'HH:mma dd/MM/yyyy";

    public static final String MCA_CA_REPORT = "ca_report";
    public static final String MCA_ADJ_REPORT = "adjustment_report";

    public static final String DEFAULT_CALENDAR_NAME = "DEFAULT_CALENDAR";
    public static final String WMR_CALENDAR_NAME = "FX WM";

    public static final String INDEX_TEMPLATE_NONE = "NONE";

    public static final String ARCHIVED_FILE_LOCATION = "archivedFileLocation";
    public static final String INDEX_IMPORT_JOB_PATH_KEY = "path";
    public static final String EXISTING_INDEX_GROUP_FLAG = "Existing_Index_Group_Flag";

    public static final String UNITED_STATES_RATE = "UNITED STATES RATE";

    public static final int DEFAULT_PROVIDER_ID = 2;

    public static final String DEFAULT_REGION = "DEFAULT_REGION";


}
