import javax.swing.SwingUtilities;

import controller.HCController;
import model.HCModel;
import view.HCView;

public class HCApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HCModel model = new HCModel();
            HCView view = new HCView();
            HCController controller = new HCController(model, view);
            view.setController(controller);
            view.setVisible(true);
        });
    }
}
