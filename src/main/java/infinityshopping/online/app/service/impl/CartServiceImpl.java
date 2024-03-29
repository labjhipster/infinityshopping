package infinityshopping.online.app.service.impl;

import infinityshopping.online.app.domain.User;
import infinityshopping.online.app.repository.CartRepository;
import infinityshopping.online.app.repository.UserRepository;
import infinityshopping.online.app.security.SecurityUtils;
import infinityshopping.online.app.service.CartService;
import infinityshopping.online.app.service.errors.UserNotFoundException;
import infinityshopping.online.app.service.dto.CartDtoAmountOfCartGross;
import infinityshopping.online.app.service.dto.CartDtoAmountsGross;
import infinityshopping.online.app.service.mapper.CartMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartServiceImpl implements CartService {

  private final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

  private final CartRepository cartRepository;

  private final UserRepository userRepository;

  private final CartMapper cartMapper;

  private User currentLoggedUser;

  public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository,
      CartMapper cartMapper) {
    this.cartRepository = cartRepository;
    this.userRepository = userRepository;
    this.cartMapper = cartMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<CartDtoAmountsGross> findByUserIdAllAmountsGross() {
    log.debug("REST request to get all the amounts gross of a current logged user");
    currentLoggedUser = checkIfUserExist();

    return cartRepository.findByUserId(currentLoggedUser.getId())
        .map(cartMapper::toDtoAmountsGross);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<CartDtoAmountOfCartGross> findByUserIdAmountOfCartGross() {
    log.debug("REST request to get the amount of cart gross of a current logged user");
    currentLoggedUser = checkIfUserExist();

    return cartRepository.findByUserId(currentLoggedUser.getId())
        .map(cartMapper::toDtoAmountOfCartGross);
  }

  private User checkIfUserExist() {
    return userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()
            .orElseThrow(UserNotFoundException::new))
        .orElseThrow(UserNotFoundException::new);
  }
}
