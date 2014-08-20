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
package org.xwiki.contrib.dynamichierarchy.test.ui.po;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.xwiki.test.ui.po.ViewPage;

/**
 * Represents the Dynamic Hierarchy view page.
 */
public class DynamicHierarchyMacroView extends ViewPage
{
    public DynamicHierarchyMacroView(ViewPage vp)
    {
        // Waits the display of nodes from loaded data.
        vp.waitUntilElementIsVisible(By.className("jstree-node"));
    }

    /**
     * @return the number of node of the Dynamic Hierarchy
     */
    public int getNodesCount()
    {
        List<WebElement> elements = getUtil().findElementsWithoutWaiting(getDriver(), By.className("jstree-node"));
        return elements.size();
    }

    /**
     * @return the selected node of the Dynamic Hierarchy
     */
    public WebElement getSelectedNode()
    {
        return getUtil().findElementWithoutWaiting(getDriver(), By.className("jstree-clicked"));
    }
}
