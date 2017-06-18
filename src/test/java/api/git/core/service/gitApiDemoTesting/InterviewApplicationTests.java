package api.git.core.service.gitApiDemoTesting;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.service.GistService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNotNull;

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
        gist = shouldCreateGist();
        Gist userGist = getSingleGist(gist.getId());
        assertNotNull(userGist);
        deleteWantedGist(gist.getId());
        System.out.println("TEST");
        try {
            getSingleGist(gist.getId());
        } catch (RequestException e) {
            assertThat("Gist wasn't deleted. ", e.getMessage().contains("Not Found"), is(true));
        }
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

