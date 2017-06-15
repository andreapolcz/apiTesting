package api.git.core.service.interview;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.GistService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InterviewApplicationTests {

    GistService gistService = new GistService();
    GitHubClient client = new GitHubClient();

    @Autowired
    private Configuration config;


    @Before
    public void authenticate() throws IOException {
        setOauthService(config.getGitToken());
        setCredentials(config.getGitUserName(), config.getGitPassw0rd());
    }

    @Test
    public void shouldCreateAndDeleteUserGist() throws IOException {
        Gist gist;
        List<Gist> userGists;
        gist = shouldCreateGist();
        userGists = getAllGists(config.getGitUserName());
        for (Gist currentGist : userGists) {
            if (gist.getId().equals(currentGist.getId())) {
                System.out.println("ITS HERE!");
            }
        }

        Gist userGist = getSingleGist(gist.getId());
        deleteWantedGist(gist.getId());
    }

    private void setOauthService(String token) throws IOException {
        client.setOAuth2Token(token);
    }

    private void setCredentials(String username, String gitPassw0rd) {
        gistService.getClient().setCredentials(username, gitPassw0rd);
    }

    private Gist setGistRequiredFields() {
        GistFile file = new GistFile();
        file.setContent(Constants.FILE_CONTENT);
        Gist gist = new Gist();
        gist.setDescription(Constants.GIST_DISCRIPTION);
        gist.setFiles(Collections.singletonMap(Constants.GIST_NAME, file));
        return gist;
    }

    private Gist shouldCreateGist() throws IOException {
        return gistService.createGist(setGistRequiredFields()); //returns the created gist
    }

    private List<Gist> getAllGists(String username) throws IOException {
        return gistService.getGists(username);
    }

    private Gist getSingleGist(String gistId) throws IOException {
        return gistService.getGist(gistId);
    }

    private void deleteWantedGist(String gistId) throws IOException {
        gistService.deleteGist(gistId);
    }


}

