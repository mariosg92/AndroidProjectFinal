package com.example.myapp.modelos;

public class ConfiguracionDB {

    public static final String HOSTDB = "infsalinas.sytes.net";
    public static final String NOMBREDB = "msgclothingdb";
    public static final String USUARIODB = "damserver1";
    public static final String CLAVEDB = "dam1234";
    private static final String OPCIONES = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    public static final String PUERTOMYSQL = "5306";
    public static final String URLMYSQL = "jdbc:mysql://"+ HOSTDB + ":" + PUERTOMYSQL+"/" + NOMBREDB + OPCIONES;

    //----------------------------------------------------------....
}
