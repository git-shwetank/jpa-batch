package com.example.demo.generic;


import org.joda.time.format.DateTimeFormat;

public interface Constants {

    /***
     * Date Time Format
     */
    String DATE_TIME_FORMAT_ISO = "yyyy-MM-dd'T'HH:mm:ssXXX";
    String DATE_TIME_HUMAN_FORMAT = "yyyy-MM-dd HH:mm:ss";
    String JODA_DATE_TIME_FORMAT_ISO = "yyyy-MM-dd'T'HH:mm:ssZZ";
    String JODA_LOCAL_TIME_FORMAT = "HH:mm:ss";
    String DATE_FORMAT_ISO = "yyyy-MM-dd";


    org.joda.time.format.DateTimeFormatter DATETIME_HUMAN_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    org.joda.time.format.DateTimeFormatter DATE_TIME_ISO_FORMATTER = DateTimeFormat.forPattern(DATE_TIME_FORMAT_ISO);
    org.joda.time.format.DateTimeFormatter JODA_LOCAL_TIME_FORMATTER = DateTimeFormat.forPattern(JODA_LOCAL_TIME_FORMAT);
    /**
     * Protocol
     */
    String WS = "ws";
    String WSS = "wss";
    String HTTP = "http";
    String HTTPS = "https";

    /**
     * CONNECTION_TOKEN holds an authenticated indication of a valid Client (e.g. TR and BBG will have different & unique connection-token values. Also this value is verified when granting a session.
     */
    String CONNECTION_TOKEN = "connection-token";

    /***
     * Topic information
     */
    String USER_TOPIC_PREFIX = "/user";
    String USER_TOPIC_STATUS_MESSAGE = "/user/queue/status";
    String TOPIC_STATUS_MESSAGE = "/queue/status";
    String TOPIC_HEART_BEAT_MESSAGE = "/topic/heartbeat";
    String TOPIC_MARKET_DATA_MESSAGE = "/topic/marketdata";


    /***
     * Message Headers
     */
    String MESSAGE_HEADER_SIMP_MESSAGE_TYPE = "simpMessageType";
    String MESSAGE_HEADER_STOMP_COMMAND = "stompCommand";
    String MESSAGE_HEADER_SIMP_DESTINATION = "simpDestination";
    String MESSAGE_HEADER_DESTINATION = "destination";
    String MESSAGE_HEADER_SIMP_USER = "simpUser";
    String MESSAGE_HEADER_SIMP_SESSIONID = "simpSessionId";
    String MESSAGE_HEADER_SIMP_SUBSCRIPTIONID = "simpSubscriptionId";
    String MESSAGE_HEADER_CONVERSION_HINT = "conversionHint";

    String MESSAGE_HEADER_FID_LIST = "fidList";

    //Subscription status
    String MESSAGE_TYPE_SB_STATUS = "status";

    //Application Messages
    String MESSAGE_TYPE_ERROR = "error";

    //market data refresh & delta Update
    String MESSAGE_TYPE_MARKETDATA_REFRESH = "refresh";
    String MESSAGE_TYPE_MARKETDATA_UPDATE = "update";
    String MESSAGE_TYPE_INDEX_STATIC = "index_static";
    String MESSAGE_TYPE_INDEX_STATIC_UPDATE = "index_static_update";

    String MESSAGE_TYPE_MARKETDATA_LOGIN = "login";
    String MESSAGE_TYPE_MARKETDATA_LOGOUT = "logout";
    String MESSAGE_TYPE_MARKETDATA_WS_STATUS = "work_flow_status";
    String MESSAGE_TYPE_MARKETDATA_INDEX_VALUE = "indexvalue";
    String MESSAGE_TYPE_QUERY = "query";
    String MESSAGE_TYPE_APP_RESPONSE = "response";
    String MESSAGE_TYPE_MARKETDATA_SYNC_CACHE_STATIC = "sync_cache_static";
    String MESSAGE_TYPE_SYNC_FX_SNAPSHOT = "sync_fx_snapshot";
    /***
     * Web socket End points
     */
    String WEBSOCKET_END_POINT = "/rtindexstream";
    String WEBSOCKET_TOPICS_START_WTIH = "/topic/";
    String WEBSOCKET_USER_QUEUE_START_WITH = "/queue/";
    String WEBSOCKET_ALLOWED_ORIGIN = "*";
    String WEBSOCKET_APPLICATION_PREFIX = "/app";

    /***
     * Rest API End Points
     */
    String API_BASE = "/api";
    String API_BASE_URI_V1 = API_BASE + "/v1";
    String REST_EP_ERROR = "/error";
    String REST_EP_LOGIN = API_BASE_URI_V1 + "/login";
    String REST_EP_LOGOUT = API_BASE_URI_V1 + "/logout";
    String REST_EP_CACHE_INDEX = API_BASE_URI_V1 + "/cache/index";
    String REST_EP_CACHE_GET_WORK_FLOW_STATUS = "/get_work_flow_status";
    String REST_EP_CACHE_UPD_WORK_FLOW_STATUS = "/update_work_flow_status";
    String REST_EP_CACHE_UPD_ALL_WORK_FLOW_STATUS = "/update_all_work_flow_status";
    String REST_EP_CACHE_ALL_WORK_FLOW_STATUS = "/all_work_flow_status";
    String REST_EP_CUSTOM_USER = API_BASE_URI_V1 + "/user";
    String REST_EP_HEALTH_CHECK = API_BASE_URI_V1 + "/healthcheck";
    String REST_EP_HEALTH_CHECK_SECURE = REST_EP_HEALTH_CHECK + "/secure";
    String REST_EP_PARAM_USER_NAME = "userName";
    String REST_EP_GET_ALL_INDEX_STATIC = "/get_all_index_static";
    String REST_EP_GET_INDEX_STATIC = API_BASE_URI_V1 + "/index_static";
    String REST_SET_COOKIE = "JSESSIONID=%s; Path=/; HttpOnly";
    String REST_EP_GET_CACHE_STATIC = "/get_cache_static";
    String REST_EP_GET_CACHE_INDEX_VALUE = "/get_cache_index_value";
    String REST_API_END_POINT_HEALTH_CHECK = API_BASE_URI_V1 + "/healthcheck";
    String REST_EP_CACHE_SYNC_CACHE_DATA = "/rte_sync_cache_static_data";
    String REST_EP_GET_CACHE_TICKER = "/get_cache_ticker";
    String REST_EP_GET_CACHE_SUBSCRIPTION_DATA = "/subscription";
    String REST_EP_GET_CACHE_PYTHON_DATA = "/python_data";
    String REST_EP_GET_CACHE_FX_SNAPSHOT_SYNC = "/fx_snapshot/sync";
    String REST_EP_CACHE_DATA = API_BASE_URI_V1 + "/cache";

    /**
     * Rest API Response text
     */
    String REST_API_RESPONSE_LOGIN_SUCCESS = "Logged in successfully";
    String REST_API_RESPONSE_LOGOUT_SUCCESS = "Logged out successfully";
    String REST_API_RESPONSE_ACCESS_DENIED = "Access denied";
    String REST_API_RESPONSE_NO_VALID_SESSION = "Invalid session";
    String REST_API_RESPONSE_UNAUTHORIZED_ENTRY_POINT = "Unauthorized entry point access";
    String REST_API_RESPONSE_ONLY_POST_METHOD_IS_ALLOWED = "\'%s\' method not supported for authentication. Only \'POST\' method is allowed";
    String REST_API_RESPONSE_BASIC_AUTH_NOT_ALLOWED = "Basic authentication not allowed";
    String REST_API_RESPONSE_TEXT_INDEX_STATIC = "Successfully retrieved %s static record(s)";
    String REST_API_RESPONSE_TEXT_INDEX_STATIC_FAILED = "Failed to get index static data";
    String REST_API_RESPONSE_BAD_CREDENTIALS = "Bad credentials";

    /**
     * Rest API Response status
     */
    String REST_API_RESPONSE_STATUS_OK = "OK";
    String REST_API_RESPONSE_STATUS_FAILED = "FAILED";


    /**
     * Case values
     */
    String DATA_SUBSCRIPTION_CAHCE_NAME = "DATA_CAHCE";
    String DATA_SUBSCRIPTION_KEY_FORMAT = "%s###%s";
    String INDEX_CACHE_KEY_FORMAT = "%s###%s";
    String INDEX_VALUE_CACHE_KEY_FORMAT = "%s####%s";
    String CONSTITUENT_KEY_FORMAT = "%s##%s##%s";

    /**
     * Database user
     */
    String USER_DATABASE_ADMIN = "ADMIN";
    String USER_PRICE_HELIOS_BATCH = "PRICETOHELIOS";
    String USER_PRICE_PROCESSOR_BATCH = "PRICEPROCESSOR";

    /***
     * Batch import Job Parameter
     */
    String BATCH_JOB_PARAM_USER_ID = "USER_ID";
    String BATCH_JOB_PARAM_IMPORT_INPUT_FILE_PATH = "BATCH_IMPORT_INPUT_FILE_PATH";
    String BATCH_JOB_PARAM_ARCHIVE_FILE_PATH = "BATCH_ARCHIVE_FILE_PATH";


    /***
     * Decimal values
     */
    int EXTRA_LARGE_DIMENSION = 16;
    int LARGE_DIMENSION = 12;
    int MEDIUM_DIMENSION = 10;
    int SMALL_DIMENSION = 6;
    int EXTRA_SMALL_DIMENSION = 4;

    //RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    int EXIT_CODE_GRACEFUL_SHUTDOWN = 100;
    int EXIT_CODE_FORCE_SHUTDOWN = 101;
    int INDEX_CALC_POOL_SIZE = 20;
    int INDEX_CALC_MAX_POOL_SIZE = 16;
    int SCHEDULER_MAX_POOL_SIZE = 50;
    /**
     * comments
     */
    String INDEX_VALUE_COMMENTS_EOD = "Index value from eod";
    String INDEX_VALUE_COMMENTS_CB = "IndexValue from CB";

    /**
     * view Daily Constituent
     */
    String DAILY_CONSTITUENT_POSITION_EOD = "EOD";
    String DAILY_CONSTITUENT_POSITION_SOD = "SOD";


    /***
     * Index Calculation
     */

    String PYTHON_INPUT_PARAM_NAME = "json_string";
    String PYTHON_OUTPUT_PARAM_NAME = "result";
    String FXRATE_BASE_CURRENCY = "USD";
    int REBASE_LOGIC_MIN = 1;
    int REBASE_LOGIC_MAX = 2;
    /****
     * todo need to confirm with OPS
     */
    String LAST_DONE_PRICE = "6";

    /**
     * mail template
     */
    String THRESHOLD_CHECK_MAIL_TEMPLATE = "templates/report/email/thresholdCheckBreachedEmail_template.json";
    String CIRCUITBREAKER_BREACHED_MAIL_TEMPLATE = "templates/report/email/circuitBreakerBreachedEmail_template.json";
    String THRESHOLD_RESUMED_MAIL_TEMPLATE = "templates/report/email/thresholdCheckResumedEmail_template.json";
    String CIRCUITBREAKER_RESUMED_MAIL_TEMPLATE = "templates/report/email/circuitBreakerResumedEmail_template.json";
    String MAIL_TEMPLATE_INDEX_ID = "indexId";
    String MAIL_TEMPLATE_INDEX_TYPE = "indexType";

    /***
     * Parameter info
     */
    String PARAMETER_DIVISOR = "Divisor";
    String PARAMETER_DIVISOR_EOD = "DivisorEOD";
    String PARAMETER_DIVISOR_SOD = "DivisorSOD";
    String PARAMETER_DAY_COUNT = "Day Count";
    String PARAMETER_FEE = "Fee";
    String REBALANCE_FEE = "Rebalance Fee";
    String PARAMETER_ACD = "ACD";
    String PARAMETER_UACD = "UACD";
    String PARAMETER_IAAF = "IAAF";
    String PARAMETER_IAF = "IAF";
    String PARAMETER_EI = "EI";
    String PARAMETER_RUNNING_ADJUSTMENT = "Running Adjustment";
    String PARAMETER_LEVERAGE_FACTOR = "Leverage factor";
    String PARAMETER_TOTAL_COST = "Total cost";
    String PARAMETER_RATES_ID_START_WITH = "Rates_id_";

    /**
     *
     */
    String ASIA_SINGAPORE = "Asia/Singapore";
    String EUROPE_LONDON = "Europe/London";

    /***
     * Email thread config
     */
    int EMAIL_CORE_POOL_SIZE = 4;
    int EMAIL_MAX_POOL_SIZE = 4;
    int AUTO_TICKER_CREATION_CORE_POOL_SIZE = 4;
    int AUTO_TICKER_CREATION_MAX_POOL_SIZE = 16;
    int INDEX_VALUE_REPORT_GEN_CORE_POOL_SIZE = 4;

    /***
     * Rest API End Points
     */
    String REST_API_END_POINT_LOGIN = "/api/v1/login";
    String REST_API_END_POINT_LOGOUT = "/api/v1/logout";
    String REST_API_END_POINT_ERROR = "/error";
    int REST_BCRYPT_STRENGTH = 12;


    /**
     * User role
     */
    String ROLE_USER = "USER";
    String ROLE_PUBLISHER = "PUBLISHER";


    /**
     * API JsonProperty
     */
    String API_JSONPROPERTY_CODE = "code";
    String API_JSONPROPERTY_TYPE = "type";
    String API_JSONPROPERTY_TS = "ts";
    String API_JSONPROPERTY_DATA = "data";
    String API_JSONPROPERTY_STATUS = "status";
    String API_JSONPROPERTY_TEXT = "text";
    String API_JSONPROPERTY_NAME = "name";
    String API_JSONPROPERTY_OPEN = "open";
    String API_JSONPROPERTY_HIGH = "high";
    String API_JSONPROPERTY_LOW = "low";
    String API_JSONPROPERTY_LAST = "last";
    String API_JSONPROPERTY_CLOSE = "close";

    /**
     * Rte Index Attribute
     */
    String INDEX_ATT_INDEXID = "indexId";
    String INDEX_ATT_ID = "id";
    String INDEX_ATT_TYPE = "indexType";
    String INDEX_ATT_NAME = "name";
    String INDEX_ATT_RTEMETHODOLOGY = "rteMethodology";
    String INDEX_ATT_ACTIVE = "active";
    String INDEX_ATT_CALCSTARTTIME = "calculationStartTime";
    String INDEX_ATT_CALCENDTIME = "calculationEndTime";
    String INDEX_ATT_THRESHOLDVALUE = "thresholdValue";
    String INDEX_ATT_CBTHRESHOLDVALUE = "cbThresholdValue";
    String INDEX_ATT_CBCOOLDOWNDURATION = "cbCooldownDuration";
    String INDEX_ATT_CBOBSERVATIONDURATION = "cbObservationDuration";
    String INDEX_ATT_PARTITIONID = "partitionId";
    String INDEX_ATT_CALCINTERVAL = "calculationInterval";
    String INDEX_ATT_REBASELOGIC = "rebaseLogic";
    String INDEX_ATT_CLOSECALCDURATION = "closeCalDuration";
    String INDEX_ATT_FX_SNAPSHOT_TIME = "fxSnapshotTime";
    String INDEX_ATT_ENABLE_PUBLISHING = "enablePublishing";
    String INDEX_ATT_DECIMAL_PLACES = "decimalPlaces";
    String INDEX_ATT_LEGACY_CODE = "legacyCode";

    /**
     * Rte Currency Attribute
     */
    String CURR_ATT_TICKERID = "tickerId";
    String CURR_ATT_PROVIDERID = "providerId";
    String CURR_ATT_CURRENCYCODE = "currencyCode";
    String CURR_ATT_CODE = "tickerCode";
    String CURR_ATT_NAME = "name";
    String CURR_ATT_STARTTIME = "startTime";
    String CURR_ATT_STOPTIME = "stopTime";
    String CURR_ATT_REGIONID = "regionId";

    /**
     * Region settings
     */
    String REG_ATT_TIME_ZONE = "timeZone";
    String REG_ATT_NAME = "name";
    String REG_ATT_STARTTIME = "startTime";
    String REG_ATT_STOPTIME = "stopTime";
    String REG_ATT_REGIONID = "regionId";

    /**
     * Region Field settings
     */
    String REG_FIELD_ATT_PROVIDER = "provider";
    String REG_FIELD_ATT_REGION = "region";
    String REG_FIELD_ATT_ASSETTYPE = "assetType";
    String REG_FIELD_ATT_FIELDNAME = "fieldName";
    String REG_FIELD_ATT_FIELDID = "fieldId";


    /**
     * Cache settings
     */
    String CACHE_ATT_CACHEID = "cacheId";
    String CACHE_ATT_TIME_ZONE = "timeZone";
    String CACHE_ATT_REFRESHTIME = "refreshTime";

    /**
     * Cache settings
     */
    String WFS_ATT_ID = "id";
    String WFS_ATT_INDEX_TYPE = "indexType";
    String WFS_ATT_BUSINESS_DATE = "businessdate";
    String WFS_ATT_STATUS = "status";
    String REST_PARAM_INDEX_ID = "indexId";
    String REST_PARAM_INDEX_TYPE = "indexType";
    String REST_PARAM_INDEX_CAL_STATUS_TYPE = "indexCalStatusType";
    String REST_PARAM_UPDATED_BY = "updatedBy";
    String REST_PARAM_INDEX_IDS = "indexIds";
    String REST_PARAM_TICKER_CODE = "tickerCode";
    String REST_PARAM_PROVIDER_CODE = "providerCode";
    String REST_PARAM_TOPIC = "topic";

    String DEFAULT_REGION = "DEFAULT_REGION";
    String CURRENCY_REGION = "CURRENCY";


    /**
     * Dashboard
     */
    String ZERO_VALUE = "0.000000000000";

    /***
     *
     */
    String BATCH_JOBS_KILL_STATUS_SUCCESS = "SUCCESS";

    /***
     * Providers
     */
    String PROVIDER_CODE_BBG = "BBG";

    String DL_PRICE_TYPE_DELIMITER = ";";

    /***
     * data licence request & response file
     */
    String DATA_LICENCE_FILE_PREFIX = "Helios";
    String DATA_LICENCE_RES_FILE_SUFFIX = "csv";
    String DATA_LICENCE_REQ_FILE_SUFFIX = "req";
    String DATA_LICENCE_REPORT_NAME_VALIDATION_PATTERN = "[a-zA-Z0-9+-.^_]*";
    String DATA_LICENCE_REPORT_NAME_VALIDATION_PATTERN_MSG = "Report name should be " + DATA_LICENCE_REPORT_NAME_VALIDATION_PATTERN;
    String ADHOC_DATA_LICENCE_REPORT_NAME_VALIDATION_PATTERN = "[0-9]{8}[a-zA-Z0-9+-.^_]*";
    String ADHOC_DATA_LICENCE_REPORT_NAME_VALIDATION_PATTERN_MSG = "Report name should be " + ADHOC_DATA_LICENCE_REPORT_NAME_VALIDATION_PATTERN;
    int DATA_LICENCE_REPORT_NAME_VALIDATION_LEN_MIN = 1;
    int DATA_LICENCE_REPORT_NAME_VALIDATION_LEN_MAX = 14;
    String DATA_LICENCE_REPORT_NAME_VALIDATION_LEN_MSG = "Report name(14 char) should be 25 chars including PREFIX 'Helios' and FileExtension '.csv'";
    String DATA_LICENCE_DEFAULT_SFG_ROUTING_SUFFIX = "HCI_GROUP_HCI_OPS";

    String DATA_LICENCE_REQEUST_ENABLE_DISABLE_ACTION = "Enable/Disable";
    String DATA_LICENCE_REQEUST_DELETE_ACTION = "Delete";
    String DATA_LICENCE_REQEUST_OVERRIDE_SUCCESS_MSG = "%s [{%s} number] of jobs";
    String DATA_LICENCE_REQEUST_OVERRIDE_FAILURE_MSG = "Validation Error no checkbox selected";

    String CALENDAR_DELETE_SUCCESS_MSG = "Deleted [{%s}] Calenders";

    /****
     * todo need to confirm with OPS
     */
    String TREP_FID_LAST = "6";
    String TREP_FID_BID = "22";
    String TREP_FID_ASK = "25";
    String TREP_FID_MID = "134";
    String TREP_FID_OFFICIAL_CLOSE = "3372";
    String TICKER_PRICE_LATEST = "LATEST";
    String TREP_FID_SETTLEMENT_PRC = "70";
    String TREP_FID_SETTLE_DATE = "288";
    String TREP_FID_SETTLE_TIME = "6730";
    String TREP_FID_TRADE_DATE = "16";
    String TREP_FID_TRADE_TIME = "18";
    String TREP_FID_OFFICIAL_CLOSE_DATE = "6762";
    String TREP_FID_OFFICIAL_CLOSE_TIME = "6377";
    String TREP_FID_HISTORIC_CLOSE = "21";
    String TREP_FID_HIST_CLOSE_DATE = "79";

    /***
     * fid type
     */
    String FIELD_DATA_TYPE_DATE = "DATE";
    String FIELD_DATA_TYPE_PRICE = "PRICE";
    String FIELD_DATA_TYPE_TIME = "TIME";


    String PROVIDER_CODE_TREP = "TREP";

    String STRING_ALL = "ALL";
    String STRING_SELECTED = "selected";
    String STRING_EMPTY = "";
    String STRING_NA = "NA";
    String STRING_OPTION_LIST_FORMAT = "%s / %s";
    String STRING_VIEW_MODEL = "viewModel";
    String SELECT_ANY_INDEX = "-- Select Any Index--";
    String SELECT_ANY_TICKER = "-- Select Any Ticker--";
    String STRING_CHECKED = "checked";

    /**
     * SB Report template
     */
    String SB_INDEX_REPORT = "index";
    String SB_EOD_SOD_REPORT = "eod_sod";
    String SB_TRACK_REPORT = "track";

    String INDEX_GROUP_REPORT_NAME = "%{indexGroupName}";
    String INDEX_REPORT_NAME = "%{indexName}";
    String INDEX_CURRENCY_REPORT_NAME = "%{indexCurrencyName}";

    /**
     * CorpClark
     */
    //TODO create enum
    final String STATUS_A = "A"; //Added
    final String STATUS_M = "M"; //Modified
    final String STATUS_D = "D"; //Deleted

    /***
     *
     */
    String DELAYED_TICKER_SUFFIX = "/";

    String STRING_MEGA_BYTE = "MB";

    long LONG_MEGA_BYTE = 1024l * 1024l;

    final String JOB_PARAM_FILENAME = "fileName";
}

