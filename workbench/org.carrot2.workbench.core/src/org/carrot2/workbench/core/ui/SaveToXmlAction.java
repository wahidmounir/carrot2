package org.carrot2.workbench.core.ui;

import java.io.*;

import org.carrot2.workbench.core.WorkbenchCorePlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class SaveToXmlAction extends Action
{
    @Override
    public void run()
    {
        ResultsEditor results =
            (ResultsEditor) WorkbenchCorePlugin.getDefault().getWorkbench()
                .getActiveWorkbenchWindow().getActivePage().getActiveEditor();
        SaveToXmlDialog dialog =
            new SaveToXmlDialog(Display.getDefault().getActiveShell(), results
                .getPartName());
        int status = dialog.open();
        if (status == Window.CANCEL)
        {
            return;
        }
        try
        {
            File destinationFile = new File(dialog.getFilePath());
            if (!destinationFile.exists())
            {
                destinationFile.createNewFile();
            }
            Writer writer = new FileWriter(destinationFile);
            results.getCurrentContent().serialize(writer, dialog.saveDocuments(),
                dialog.saveClusters());
            writer.close();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Save to XML action failed.", e);
        }
    }

    @Override
    public ImageDescriptor getImageDescriptor()
    {
        return AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.ui",
            "icons/full/etool16/save_edit.gif");
    }
}
