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
package org.xwiki.contrib.dynamichierarchy.test.ui;

import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.xwiki.contrib.dynamichierarchy.test.ui.po.DynamicHierarchyMacroView;
import org.xwiki.test.ui.AbstractTest;
import org.xwiki.test.ui.po.ViewPage;

import static junit.framework.Assert.assertEquals;

//import org.xwiki.test.ui.SuperAdminAuthenticationRule;

/**
 * UI tests for the Dynamic Hierarchy feature.
 */
public class DynamicHierarchyTest extends AbstractTest
{
    @Test
    public void dynamicHierarchyMacro() throws Exception
    {
        // Remove existing pages
        getUtil().deleteSpace(getTestClassName());

        // Create tree with 3 root documents
        getUtil().createPage(getTestClassName(), "Document1", null, null);
        getUtil().createPage(getTestClassName(), "Document11", null, null, null, getTestClassName() + ".Document1");
        getUtil().createPage(getTestClassName(), "Document12", null, null, null, getTestClassName() + ".Document1");

        getUtil().createPage(getTestClassName(), "Document2", null, null);
        getUtil().createPage(getTestClassName(), "Document3", null, null);

        String rootDocuments =
            getTestClassName() + ".Document1," + getTestClassName() + ".Document2," + getTestClassName() + ".Document3";

        // Set Document12 selected on tree
        String openNode = getTestClassName() + ".Document12";

        String macro = "{{dynamicHierarchy rootdocuments='" + rootDocuments + "' displayvalue='displayTitle' "
            + "checkChilds='true' openNode='" + openNode + "'/}}";

        ViewPage vp = getUtil().createPage(getTestClassName(), "HierarchyTest", macro, "");

        DynamicHierarchyMacroView hierarchyPage = new DynamicHierarchyMacroView(vp);

        // Verify number of nodes is 5.
        assertEquals(5, hierarchyPage.getNodesCount());

        // verify that the document selected in tree is "Document12"
        WebElement selectedNode = hierarchyPage.getSelectedNode();
        assertEquals(selectedNode.getAttribute("href"), getUtil().getURL(getTestClassName(), "Document12", "view"));
    }
}
