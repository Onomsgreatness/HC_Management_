/**
 * Author: Onome Abuku <oa22aed@herts.ac.uk>
 *     ID: 21092431
 *     References: Dr. John Kanyaru, BookShop Example.
 */

import javax.swing.SwingUtilities;

import controller.HCController;
import model.HCModel;
import view.HCView;

public class HCApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HCModel model = new HCModel();
            System.out.println("Model created and data loaded.");

            HCView view = new HCView();
            System.out.println("View created.");

            HCController controller = new HCController(model, view);
            System.out.println("Controller created.");

            view.setController(controller);
            System.out.println("MVC components wired together.");

            view.setVisible(true);
        });
    }
}
