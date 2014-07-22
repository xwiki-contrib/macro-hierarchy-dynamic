/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.hiearchy.test.ui;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.xwiki.test.ui.AbstractTest;
import org.xwiki.test.ui.SuperAdminAuthenticationRule;

import static junit.framework.Assert.assertEquals;

/**
 * UI tests for the Dynamic Hiearchy feature.
 */
public class DynamicHiearchyTest extends AbstractTest
{
    @Rule
    public SuperAdminAuthenticationRule authenticationRule = new SuperAdminAuthenticationRule(getUtil(), getDriver());


    @Test
    public void testDynamicHiearchy() throws Exception
    {

        // Remove existing pages
        getUtil().deleteSpace(getTestClassName());

        // Create tree with 3 root documents
        getUtil().createPage(getTestClassName(), "Doocument1", null, null);
        getUtil().createPage(getTestClassName(), "Doocument11", null, null, null , getTestClassName()+".Doocument1");
        getUtil().createPage(getTestClassName(), "Doocument12", null, null, null , getTestClassName()+".Doocument1");

        getUtil().createPage(getTestClassName(), "Doocument2", null, null);
        getUtil().createPage(getTestClassName(), "Doocument3", null, null);

        String rootdocuments = getTestClassName()+".Doocument1,"+getTestClassName()+".Doocument2,"+getTestClassName()+".Doocument3";

        // Set Doocument12 selected on tree
        String openNode = getTestClassName()+".Doocument12";

        String macro = "{{dynamicHierarchy rootdocuments='"+rootdocuments+"' displayvalue='displayTitle' "
            +"checkChilds='true' openNode='"+openNode+"'/}}";

        getUtil().createPage(getTestClassName(), "HiearchyTest", macro, "");

        //Waits the display of nodes from loaded data
        getUtil().waitUntilCondition(new ExpectedCondition<Boolean>()
        {
            @Override
            public Boolean apply(WebDriver driver)
            {
                try {
                    WebElement element = getUtil().findElementWithoutWaiting(driver,
                        By.className("jstree-container-ul"));
                    return true;
                } catch (NotFoundException e) {
                    return false;
                } catch (StaleElementReferenceException e) {
                    // The element was removed from DOM in the meantime
                    return false;
                }
            }
        });

        // verify number of nodes is 5
        getUtil().waitUntilCondition(new ExpectedCondition<Boolean>()
        {
            @Override
            public Boolean apply(WebDriver driver)
            {
                try {
                    List<WebElement> elements =
                        getUtil().findElementsWithoutWaiting(driver, By.className("jstree-node"));
                    return elements.size() ==5 ? true : false;
                } catch (NotFoundException e) {
                    return false;
                } catch (StaleElementReferenceException e) {
                    // The element was removed from DOM in the meantime
                    return false;
                }
            }
        });

        // verify that the document selected in tree is "Document12"
        WebElement element = getUtil().findElementWithoutWaiting(getDriver() ,By.className("jstree-clicked"));
        assertEquals(element.getAttribute("href"),getUtil().getURL(getTestClassName(), "Doocument12", "view"));

    }
}
