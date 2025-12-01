package main.tests;

import main.data.AutoSaveGateway;
import main.data.FileAutoSaveGateway;
import main.usecase.AutoSaveInteractor;
import main.usecase.AutoSaveRequestModel;
import main.usecase.AutoSaveResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AutoSaveInteractorTest {

    private AutoSaveGateway autoSaveGateway;
    private AutoSaveInteractor autoSaveInteractor;
    private final File file = new File("autosave.txt");

    @BeforeEach
    void setUp() {
        autoSaveGateway = new FileAutoSaveGateway();
        autoSaveInteractor = new AutoSaveInteractor(autoSaveGateway);
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