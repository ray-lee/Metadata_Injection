import models.DBUtils;
import models.MDUtils;
import controllers.BAMCOLLECTIONController;
import controllers.InjectMetaDataController;


public class BAMCollectionInjectMetadata extends InjectMetadata {

	@Override
	protected String getType() {
		return "BAM Collection";
	}

	@Override
	protected InjectMetaDataController getController(String targetDir, DBUtils dbUtils, MDUtils mdUtils) {
		return new BAMCOLLECTIONController(targetDir, dbUtils, mdUtils);
	}

	public static void main(String[] args) {
		new BAMCollectionInjectMetadata().injectMetadata(args);
	}
}
