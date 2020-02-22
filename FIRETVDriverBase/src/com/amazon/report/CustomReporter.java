package com.amazon.report;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.firetv.commonpojo.AppTestDataPojo;
import com.firetv.util.UIMacroBase;

public class CustomReporter implements IReporter{
	

	@SuppressWarnings("unchecked")
	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		// TODO Auto-generated method stub
		JSONArray results = new JSONArray();
		suites.forEach(element->{results.add(createSuiteJsonObject(element));
		});
		
		try (FileWriter file = new FileWriter(outputDirectory + "/report.json")) {
            file.write(results.toJSONString());
        } catch (IOException e) {
        	
        	e.printStackTrace();
            //handle
        }
		
		
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject createSuiteJsonObject(ISuite suite)
	{
		JSONObject results = new JSONObject();
		JSONArray passedTest = new JSONArray();
		JSONArray failedTest = new JSONArray();
		JSONArray skippedTest = new JSONArray();
		JSONArray deviceDSN = new JSONArray();
	    JSONArray deviceModle = new JSONArray();
		
		suite.getResults().entrySet().forEach(element -> {
            ITestContext context = element.getValue().getTestContext();
            passedTest.addAll(createResultJsonArray(context.getPassedTests().getAllResults()));
            failedTest.addAll(createResultJsonArray(context.getFailedTests().getAllResults()));
            skippedTest.addAll(createResultJsonArray(context.getSkippedTests().getAllResults()));
            deviceDSN.add(AppTestDataPojo.getDsn());
            deviceModle.add(AppTestDataPojo.getDeviceOS());
	});
		
		results.put("name", suite.getName());
        results.put("passed", passedTest);
        results.put("failed", failedTest);
        results.put("skipped", skippedTest);
        results.put("DSN", deviceDSN);
        results.put("OS", deviceModle);
        return results;
	
	
	}
	
	@SuppressWarnings("unchecked")
	public JSONArray createResultJsonArray(Set<ITestResult> results)
	{
		JSONArray result = new JSONArray();
        results.forEach(element ->{
            JSONObject currentJsonResult = new JSONObject();
            currentJsonResult.put("name", element.getName());
            result.add(currentJsonResult);
        });
        return result;
		
	}
}
