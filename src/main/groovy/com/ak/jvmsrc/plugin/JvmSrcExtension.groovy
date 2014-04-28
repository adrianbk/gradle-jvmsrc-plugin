package com.ak.jvmsrc.plugin

class JvmSrcExtension {
    public static final String DEFAULT_PACKAGE = "com.nopackage"
    String packageName = DEFAULT_PACKAGE

    void setPackageName(String packageName) {
        this.packageName = packageName ?: DEFAULT_PACKAGE
    }
}
