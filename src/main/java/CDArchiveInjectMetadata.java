import models.DBUtils;
import models.MDUtils;
import controllers.BAMCDArchiveController;
import controllers.InjectMetaDataController;


public class CDArchiveInjectMetadata extends InjectMetadata {

	@Override
	protected String getType() {
		return "Archive";
	}

	@Override
	protected InjectMetaDataController getController(String targetDir, DBUtils dbUtils, MDUtils mdUtils) {
		return new BAMCDArchiveController(targetDir, dbUtils, mdUtils);
	}

	public static void main(String[] args) {
		new CDArchiveInjectMetadata().injectMetadata(args);
	}
}

