package com.example.bloombackend.bottlemsg;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.bloombackend.bottlemsg.entity.BottleMessageEntity;
import com.example.bloombackend.bottlemsg.repository.BottleMessageRepository;
import com.example.bloombackend.oauth.OAuthProvider;
import com.example.bloombackend.user.entity.UserEntity;
import com.example.bloombackend.user.repository.UserRepository;

@Component
public class DefaultMessageInitializer {
	private final BottleMessageRepository bottleMessageRepository;
	private final UserRepository userRepository;

	@Autowired
	public DefaultMessageInitializer(BottleMessageRepository bottleMessageRepository, UserRepository userRepository) {
		this.bottleMessageRepository = bottleMessageRepository;
		this.userRepository = userRepository;
	}

	@PostConstruct
	public void init() {
		UserEntity admin = UserEntity.builder()
			.provider(OAuthProvider.KAKAO)
			.name("admin")
			.build();
		userRepository.save(admin);

		if (bottleMessageRepository.count() == 0) {
			BottleMessageEntity message1 = BottleMessageEntity.builder()
				.content("비록 지금은 느리게 가는 것처럼 보여도, 그 작은 한 걸음 한 걸음이 결국 큰 변화를 만들 것입니다. 끝까지 함께 갑시다!")
				.title("작은 한 걸음이 큰 변화를 만듭니다!")
				.postcardUrl(
					"https://bloom-bucket-8430.s3.ap-northeast-2.amazonaws.com/postCard/KakaoTalk_20240919_002957024.jpg")
				.user(admin)
				.build();

			BottleMessageEntity message2 = BottleMessageEntity.builder()
				.content("지금까지 해온 모든 노력이 빛을 발할 때가 옵니다. 당신의 노력은 결코 헛되지 않을 거예요. 자신을 믿고 계속 나아가세요!")
				.title("당신은 충분히 잘하고 있어요!")
				.postcardUrl(
					"https://bloom-bucket-8430.s3.ap-northeast-2.amazonaws.com/postCard/KakaoTalk_20240919_002957024.jpg")
				.user(admin)
				.build();

			bottleMessageRepository.saveAll(List.of(message1, message2));
		} else {
			System.out.println("기본 메시지가 이미 존재합니다.");
		}
	}
}
