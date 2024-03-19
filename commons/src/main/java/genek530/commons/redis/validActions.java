package genek530.commons.redis;

public enum validActions {
    //proxy != Standalone

    restartedStandalone,




    proxyStartup,
    startupdata,


    updateAuthenticatedUsers,

    //todo teoretycznie tutaj jest problem bo idzie sendMultiplePermsUpdate i moglby pojedynczo permsji nie wysylac skoro juz w mc->dsc tak to dziala.
    ackAuthorization,


    adddsctoMC,
    remdsctoMC,



    //todo tutaj trzeba zrobic jeszcze ze proxy wysyla pakiet do innych podserverów z innym LP (techblok nie potrzeubje tego to tego nie dotykam)
    addMCtoDsc,
    remMCtoDsc,


    syncuserstandAlone,
    createSyncedUser,

    desynchronizeuser;

}
