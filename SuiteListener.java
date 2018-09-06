/*
* ******************************************************************************
* Copyright 2017-2018 Charter Communications. All Rights Reserved.
* Unauthorized copying of this file, via any medium is strictly prohibited
* Proprietary and confidential
* ******************************************************************************
*/
package SuiteUtils;

import org.testng.ISuite;
import org.testng.ISuiteListener;

/**
 * Created by Raghavendra.A on 14-06-2017.
 */
public class SuiteListener implements ISuiteListener {

    public String suiteName="Default";

    public void onStart(ISuite iSuite) {
        suiteName= iSuite.getName();
        ReportInitializer.localSuiteName=suiteName;
        System.out.println("Inside On Start");
    }

    public void onFinish(ISuite iSuite) {
    	
    }
}
