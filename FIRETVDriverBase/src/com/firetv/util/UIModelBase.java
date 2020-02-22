package com.firetv.util;


import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.ui.Select;

import com.firetv.action.DriverException;
import com.firetv.testbase.TestBase;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.Dimension;

import java.time.Duration;

import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
 

public class UIModelBase extends TestBase {

	/**
	 * 
	 * The method used to refresh the page after loading on UI.
	 * 
	 * @return
	 */
	public String getPageRefresher(){
		try {
			String pageSource = driver.getPageSource();
			return pageSource;
        } catch (WebDriverException e) {
            throw new DriverException(e);
        } catch (Exception e) {
            log.fatal("Failure running command " + e.getMessage(), e);
            throw new DriverException(e);
        }
	}
	
	
	/**
	 * The method used to read text values from the UI which are located using accessibility ID
	 * 
	 * @param id
	 * @return
	 */
	public String getTextFromAccessibiltyId(String id){
		try {
			String elements = driver.findElementByAccessibilityId(id).getAttribute("contentDescription"); 
			return elements;
        } catch (WebDriverException e) {
            throw new DriverException(e);
        } catch (Exception e) {
            log.fatal("Failure running command " + e.getMessage(), e);
            throw new DriverException(e);
        }
		
		
	}
	

	/**
	 * perform a click operation on element passing through parameter
	 * @param by
	 */
	protected static void doClickByAccessibiltyId(String by) {
		log.info("<ElementAction> click on locator  " + by);
		try {
			boolean elementIsPresent = driver.findElementByAccessibilityId(by).isEnabled();
			if (!elementIsPresent) {
				driver.findElementByAccessibilityId(by).click();
			} else {
				throw new Exception(by + " Locator is not present to click");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new DriverException(e);
		}
	}
	
	/**
	 * used to select the drop down elements from web for UI Activation
	 * @param by
	 * @param name
	 */
	public static void selectDropDownFromWeb(By by, String name){
		log.info("<ElementAction> To select element by name" + by);
		try {
			boolean isElementPresent = webDriver.findElement(by).isEnabled();
			Select selectElement = new Select(webDriver.findElement(by));
			if (isElementPresent) {
				selectElement.selectByVisibleText(name);
				waitInSec(5000);
			}
			else{
				throw new Exception(by + " Locator is not Present to select by" + name);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new DriverException(e);
		}
	}
	
	/**
	 * perform a click operation on element passing through parameter
	 * @param by
	 */
	protected static void doClick(By by) {
		log.info("<ElementAction> click on locator  " + by);
		try {
			boolean elementIsPresent = driver.findElement(by).isEnabled();
			if (elementIsPresent) {
				driver.findElement(by).click();
			} else {
				throw new Exception(by + " Locator is not present to click");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new DriverException(e);
		}
	}
	
	//Tap to an element for 250 milliseconds
	protected static void tapByElement (By by) {
		try{ 
			boolean elementIsPresent = driver.findElement(by).isEnabled();
			if(elementIsPresent) {
        new TouchAction(driver)
                .tap(tapOptions().withElement(element(driver.findElement(by))))
                .waitAction(waitOptions(Duration.ofMillis(250))).perform();  
			} else {
				throw new Exception(by + " Locator is not present to tap");
			}
		} catch(Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new DriverException(e);
		}
    }
	
	 //Tap by coordinates
    protected static void tapByCoordinates (int x,  int y) {
        new TouchAction(driver)
                .tap(point(x,y))
                .waitAction(waitOptions(Duration.ofMillis(250))).perform();
    }
    
  //Press by element
    protected static void pressByElement (By by, long seconds) {
    	try {
    		boolean isElementPresent = driver.findElement(by).isEnabled();
    		if(isElementPresent) {
        new TouchAction(driver)
                .press(element(driver.findElement(by)))
                .waitAction(waitOptions(ofSeconds(seconds)))
                .release()
                .perform();
        } else {
        	throw new Exception(by + " Locator is not present to tap");
                }
    	} catch (Exception e) {
    		log.error(e.getMessage());
			e.printStackTrace();
			throw new DriverException(e);
    	}
    }

	protected static void doClickInWeb(By by) {
		log.info("<ElementAction> click on locator  " + by);
		try {
			boolean elementIsPresent = webDriver.findElement(by).isEnabled();
			if (elementIsPresent) {
				webDriver.findElement(by).click();
			} else {
				throw new Exception(by + " Locator is not present to click");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new DriverException(e);
		}
	}
	
	
	public static void switchFromFrame(String frameIDorName){
		log.info("Switching to Frame");
		try {
			webDriver.switchTo().frame(frameIDorName);
		} catch(Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new DriverException(e);	
		}
	}
	
	public static void switchFromFrameIndex(int frameIndex){
		log.info("Switching to Frame");
		try {
			webDriver.switchTo().frame(frameIndex);
		} catch(Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new DriverException(e);	
		}
	}
	
	/**
	 * perform a click operation on element passing through parameter
	 * @param by
	 */
	protected static void switchFromFrameBy(By by) {
		log.info("Switching to Frame using By: " + by);
		try {
			webDriver.switchTo().frame(driver.findElement(by));
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new DriverException(e);
		}
	}
	
	public static void switchToDefaultContent(){
		log.info("Switching back to default content");
		try {
			webDriver.switchTo().defaultContent();
		} catch(Exception e){
			
		}
	}
	
	public static int countFrames(){
		
		try {
			int size = webDriver.findElements(By.tagName("iframe")).size();
			return size;
		} catch(Exception e){
			return 0;
		}
		
	}
	
	/**
	 * Verifying the element is present and returns a boolean result
	 * @param by
	 * @return boolean
	 */
	public Boolean isElementPresentBoolean(By by) {
		boolean element_present;
		try {
			element_present =  driver.findElement(by).isDisplayed();
		}
		catch (Exception e) {
			element_present = false;
			return element_present;
		}
		return element_present;
	}

	/**
	 * verifying the element is enabled or not
	 * @param by
	 * @return boolean
	 */
	public Boolean isEnabled(By by) {
		try {
			WebElement element = driver.findElement(by);
			return element.isEnabled();
		} catch (Exception e) {
			log.fatal("Failure running command. " + e.getMessage(), e);
			e.printStackTrace();
			throw new DriverException(e);
		}
	}

	/**
	 * Verifying the element is checked or not
	 * @param by
	 * @return boolean
	 */
	public Boolean isChecked(By by) {
		try {
			WebElement element = driver.findElement(by);
			return element.isSelected();
		} catch (Exception e) {
			log.fatal("Failure running command. " + e.getMessage(), e);
			e.printStackTrace();
			throw new DriverException(e);
		}
	}

	/**
	 * Verifying the element is present or not
	 * @param by
	 * @return boolean
	 */
	public Boolean isElementPresent(By by) {
		try {
			driver.findElement(by).isDisplayed();
			return true;
		} catch (Exception e) {
			log.fatal("Failure running command. " + e.getMessage(), e);
			e.printStackTrace();
			throw new DriverException(e);
		}
	}
	
	public Boolean isElementPresentInWeb(By by) {
		try {
			return webDriver.findElement(by).isDisplayed();
		} catch (Exception e) {
			log.fatal("Failure running command. " + e.getMessage(), e);
			e.printStackTrace();
			throw new DriverException(e);
		}
	}
	
	public Boolean isElementsExistInWeb(By by) {
		try {
			if (webDriver.findElements(by).size() != 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			log.fatal("Failure running command. " + e.getMessage(), e);
			e.printStackTrace();
			throw new DriverException(e);
		}
	}

	/**
	 * Verifying the elements are present. Parameter should return in list
	 * @param by
	 * @return
	 */
	public Boolean isElementsPresent(By by) {
		try {
			List<WebElement> elements = (List<WebElement>) driver.findElements(by);
			return elements.size() != 0;
		} catch (Exception e) {
			log.fatal("Failure running command. " + e.getMessage(), e);
			throw new DriverException(e);
		}
	}

	/**
	 * Tying the value passed through as parameter in the element
	 * @param by
	 * @param value
	 */
	protected static void doType(By by, String value) {
		log.info("<ElementAction> type on locator  " + by);
		try {
			boolean elementIsPresent = driver.findElement(by).isEnabled();
			if (elementIsPresent) {
				driver.findElement(by).clear();
				driver.findElement(by).sendKeys(value);
			} else {
				throw new Exception(by + " Locator is not present to type the" + value);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new DriverException(e);
		}
	}
	
	protected static void doTypeInWeb(By by, String value) {
		log.info("<ElementAction> type on locator  " + by);
		try {
			boolean elementIsPresent = webDriver.findElement(by).isEnabled();
			if (elementIsPresent) {
				webDriver.findElement(by).clear();
				webDriver.findElement(by).sendKeys(value);
			} else {
				throw new Exception(by + " Locator is not present to type the" + value);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new DriverException(e);
		}
	}

	/**
	 * Clearing the textbox
	 * @param by
	 */
	protected static void clearTextField(By by) {
		log.info("<ElementAction> clear on locator  " + by);
		try {
			boolean elementIsPresent = driver.findElement(by).isEnabled();
			if (elementIsPresent) {
				driver.findElement(by).clear();
			} else {
				throw new Exception(by + " Locator is not present to clear the filed");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new DriverException(e);
		}

	}
	
	public static void scroll_WebPage(int xPos, int yPos) {
		JavascriptExecutor jse = (JavascriptExecutor)getWebdriverInstance();
		String scrollCommand = String.format("scroll(%1$s, %2$s)", xPos, yPos);
		jse.executeScript(scrollCommand);
	}

	public static boolean checkAlert_Accept() {
		try {
			Alert a = driver.switchTo().alert();
			String alertText = a.getText();
			log.info("Alert message is" + alertText);
			a.accept();
			return true;
		} catch (Exception e) {
			log.error("Unable to accept alert" + e.getMessage());
			throw new DriverException(e);
		}
	}

	public static boolean checkAlert_Dismiss() {
		try {
			Alert a = driver.switchTo().alert();
			String alertText = a.getText();
			log.info("Alert message is" + alertText);
			a.dismiss();
			return true;

		} catch (Exception e) {
			log.error("Unable to dismiss alert" + e.getMessage());
			throw new DriverException(e);
		}
	}
	
	

	protected static void scrolltoElement(By ScrolltoThisElement) {
		log.info("<ElementAction> scroll on to locator" + ScrolltoThisElement);
		try {
			Coordinates coordinate = ((Locatable) ScrolltoThisElement).getCoordinates();
			coordinate.onPage();
			coordinate.inViewPort();
		} catch (ElementNotVisibleException e) {
			log.info("Unable to locate" + ScrolltoThisElement);
			log.error(e.getMessage());
			throw new DriverException(e);
		}
	}

	protected static void checkbox_Checking(By by) {
		log.info("<ElementAction> Click checkbox locator" + by);
		try {
			boolean elementIsPresent;
			elementIsPresent = driver.findElement(by).isSelected();
			if (!elementIsPresent == true) {
				driver.findElement(by).click();
			} else {
				throw new Exception(by + " Checkbox locator is not present");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new DriverException(e);
		}
	}
	
	protected static void waitInSec(int sec) {
		try {
			Thread.sleep(sec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected static void radiobutton_Select(By by) {
		log.info("<ElementAction> Click radio button on to locator" + by);
		try {
			boolean elementIsPresent;
			elementIsPresent = driver.findElement(by).isSelected();
			if (!elementIsPresent == true) {
				driver.findElement(by).click();
			} else {
				throw new Exception(by + " Locator is not present to select radio button");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DriverException(e);
		}
	}

	protected static void checkbox_Unchecking(By by) {
		log.info("<ElementAction> Unchecking checkbox on to locator" + by);
		try {
			boolean elementIsPresent;
			elementIsPresent = driver.findElement(by).isSelected();
			if (elementIsPresent == true) {
				driver.findElement(by).click();
			} else {
				throw new Exception(by + " Locator is not present to uncheck checkbox");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DriverException(e);
		}
	}

	protected static void radioButton_Deselect(By by) {
		log.info("<ElementAction> Unchecking checkbox on to locator" + by);
		try {
			boolean elementIsPresent;
			elementIsPresent = driver.findElement(by).isSelected();
			if (elementIsPresent == true) {
				driver.findElement(by).click();
			} else {
				throw new Exception(by + " Locator is not present ot deselct the radio button");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new DriverException(e);
		}

	}

	protected static void hoverWebelement(By by) {
		log.info("<ElementAction> To hover the web element" + by);
		try {
			boolean elementIsPresent;
			elementIsPresent = driver.findElement(by).isEnabled();
			if (elementIsPresent) {
				Actions builder = new Actions(driver);
				builder.moveToElement(driver.findElement(by)).perform();
				Thread.sleep(2000);
			} else {
				throw new Exception(by + " Locator is not find to hower web element");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new DriverException(e);
		}
	}

	protected static void doubleClickWebelement(By by) {
		log.info("<ElementAction> To double click the web element" + by);
		try {
			boolean elementIsPresent = driver.findElement(by).isEnabled();
			if (elementIsPresent) {
				Actions builder = new Actions(driver);
				builder.doubleClick(driver.findElement(by)).perform();
				Thread.sleep(2000);
			} else {
				throw new Exception(by + " Locator is not find to double click the element");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new DriverException(e);
		}
	}

	protected static void selectElementByNameMethod(By by, String name) {
		log.info("<ElementAction> To select element by name" + by);
		try {
			boolean elementIsPresent = driver.findElement(by).isEnabled();
			if (elementIsPresent) {
				Select selectitem = new Select(driver.findElement(by));
				selectitem.selectByVisibleText(name);
			} else {
				throw new Exception(by + " Locator is not Present to select by" + name);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new DriverException(e);
		}
	}

	protected static void selectElementByValueMethod(By by, String value) {
		log.info("<ElementAction> To select element by value" + by);
		try {
			boolean elementIsPresent = driver.findElement(by).isEnabled();
			if (elementIsPresent) {
				Select selectitem = new Select(driver.findElement(by));
				selectitem.selectByValue(value);
			} else {
				throw new Exception(by + " Locator is not Present to select by" + value);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new DriverException(e);
		}
	}

	protected static void selectElementByIndexMethod(By by, int index) {
		log.info("<ElementAction> To select element by index" + by);
		try {
			boolean elementIsPresent = driver.findElement(by).isEnabled();
			if (elementIsPresent) {
				Select selectitem = new Select(driver.findElement(by));
				selectitem.selectByIndex(index);
			} else {
				throw new Exception(by + "Locator is not Present to select by " + index);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new DriverException(e);
		}
	}
	
	public Boolean isTextPresent(By by,final String text) {
        try {
            String bodyText = driver.findElement(by).getText();
            return bodyText.contains(text);
        } catch (WebDriverException e) {
            throw new DriverException(e);
        } catch (Exception e) {
            log.fatal("Failure running command. " + e.getMessage(), e);
            throw new DriverException(e);
        }
    }
	
	public String getText(By by) {
        try {
            WebElement element = driver.findElement(by);
            return element.getText();
        } catch (WebDriverException e) {
            throw new DriverException(e);
        } catch (Exception e) {
            log.fatal("Failure running command " + e.getMessage(), e);
            throw new DriverException(e);
        }
    }
	
	public String getTextInWeb(By by) {
        try {
            WebElement element = webDriver.findElement(by);
            return element.getText();
        } catch (WebDriverException e) {
            throw new DriverException(e);
        } catch (Exception e) {
            log.fatal("Failure running command " + e.getMessage(), e);
            throw new DriverException(e);
        }
    }
	
	public String getContentDescription(By by) {
        try {
            WebElement element = driver.findElement(by);
            return element.getAttribute("contentDescription");
        } catch (WebDriverException e) {
            throw new DriverException(e);
        } catch (Exception e) {
            log.fatal("Failure running command " + e.getMessage(), e);
            throw new DriverException(e);
        }
    }
}
