package study.tipsyboy.boardApiProject.testdata;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import study.tipsyboy.boardApiProject.auth.dto.SignupRequestDto;
import study.tipsyboy.boardApiProject.auth.service.AuthService;
import study.tipsyboy.boardApiProject.posts.dto.PostsCreateRequestDto;
import study.tipsyboy.boardApiProject.posts.service.PostsService;

@RequiredArgsConstructor
@Component
public class TestDataLoader implements CommandLineRunner {

    private final AuthService authService;
    private final PostsService postsService;

    @Override
    public void run(String... args) throws Exception {
        String testEmail = "tester@test.com";
        String testPassword = "123123";
        String nickname = "테스터";
        authService.signup(new SignupRequestDto(testEmail, testPassword, nickname));

        for (int i = 0; i < 300; i++) {
            PostsCreateRequestDto requestDto = new PostsCreateRequestDto(
                    "테스트 제목 " + i,
                    "테스트 콘텐츠입니다. " + i,
                    "free");
            postsService.createPosts(testEmail, requestDto);
        }
    }
}
