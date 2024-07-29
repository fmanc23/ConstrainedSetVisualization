package utilities;

public class Assets {
    // Getting the absolute path
    private String assetPath = AbsolutePath.getAbsolutePath();
    // This string will be concatenated with the rest of the path which is needed

    protected String getExperimentsFile() {
        // Navigating to the assets folder while checking on Windows or *NIX-Like
        if (assetPath.contains("\\")) {
            assetPath = assetPath + "assets\\Experiments.csv"; // Windows version
        }
        else if (assetPath.contains("/")) {
            assetPath = assetPath + "assets/Experiments.csv"; // *NIX version
        }
        return assetPath;
    }
}
