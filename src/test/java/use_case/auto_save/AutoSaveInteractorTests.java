package use_case.auto_save;

import data_access.AutoSaveDataAccessObject;
import data_access.FileAutoSaveDataAccessObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AutoSaveInteractorTest {

    private AutoSaveDataAccessObject autoSaveDataAccessObject;
    private AutoSaveInteractor autoSaveInteractor;
    private final File file = new File("autosave.txt");

    @BeforeEach
    void setUp() {
        autoSaveDataAccessObject = new FileAutoSaveDataAccessObject();
        autoSaveInteractor = new AutoSaveInteractor(autoSaveDataAccessObject);
        if (file.exists()) file.delete();
    }

    @AfterEach
    void tearDown() {
        if (file.exists()) file.delete();
    }

    @Test
    void testSave() {
        AutoSaveRequestModel request = new AutoSaveRequestModel("Hello World");
        AutoSaveResponseModel response = autoSaveInteractor.save(request);

        assertTrue(response.isSuccess());
        assertEquals("Hello World", response.getContent());
        assertTrue(file.exists());
    }

    @Test
    void testLoad_withContent() {
        autoSaveInteractor.save(new AutoSaveRequestModel("Saved Content"));
        AutoSaveResponseModel response = autoSaveInteractor.load();

        assertTrue(response.isSuccess());
        assertEquals("Saved Content", response.getContent());
    }

    @Test
    void testLoad_withEmptyFile() throws IOException {
        file.createNewFile();
        AutoSaveResponseModel response = autoSaveInteractor.load();

        assertFalse(response.isSuccess());
        assertEquals("", response.getContent());
    }

    @Test
    void testLoad_withNullContent() {
        AutoSaveResponseModel response = autoSaveInteractor.load();
        assertFalse(response.isSuccess());
        assertEquals("", response.getContent());
    }


}