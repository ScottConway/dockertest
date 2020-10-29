package org.conway.dockertest.util;

import com.opencsv.bean.processor.StringProcessor;

public class TrimSpacesThatIntellijFormatterKeepsPuttingIn implements StringProcessor {
    @Override
    public String processString(String value) {
        return value == null ? null : value.trim();
    }

    @Override
    public void setParameterString(String value) {

    }
}
