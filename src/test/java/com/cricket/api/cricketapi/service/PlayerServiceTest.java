package com.cricket.api.cricketapi.service;

import com.cricket.api.cricketapi.dto.PlayerDTO;
import com.cricket.api.cricketapi.entity.Player;
import com.cricket.api.cricketapi.entity.Team;
import com.cricket.api.cricketapi.exception.ResourceNotFoundException;
import com.cricket.api.cricketapi.mapper.PlayerMapper;
import com.cricket.api.cricketapi.repository.PlayerRepository;
import com.cricket.api.cricketapi.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Player Service Tests")
public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PlayerMapper playerMapper;

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private PlayerService playerService;

    private Player player;
    private PlayerDTO playerDTO;

    @BeforeEach
    void setUp(){
        player = new Player();
        player.setId(1);
        player.setName("Virat Kohli");
        player.setMatches(500);
        player.setRuns(12000);
        player.setAverage(65.67);

        playerDTO = new PlayerDTO(1, "Virat Kohli", 500, 12000, 65.67, null);

    }

    @Nested
    @DisplayName("getPlayerById tests")
    class GetPlayerByIdTests {

        @Test
        @DisplayName("should return PlayerDTO when player exists")
        void getPlayerById_withValidId_returnsPlayerDTO() {
            // ARRANGE — set up what the mock should return
            when(playerRepository.findById(1)).thenReturn(Optional.of(player));
            when(playerMapper.toDTO(player)).thenReturn(playerDTO);

            // ACT — call the actual method you're testing
            PlayerDTO result = playerService.getPlayerById(1);

            // ASSERT — verify the result is what you expected
            assertNotNull(result);
            assertEquals("Virat Kohli", result.getName());
            assertEquals(12000, result.getRuns());

            // VERIFY — make sure the right methods were called
            verify(playerRepository).findById(1);
            verify(playerMapper).toDTO(player);

        }

        @Test
        @DisplayName("should throw ResourceNotFoundException when player not found")
        void getPlayerById_withInvalidId_throwsResourceNotFoundException() {
            // ARRANGE — make repository return empty
            when(playerRepository.findById(999)).thenReturn(Optional.empty());

            // ACT + ASSERT — verify exception is thrown
            ResourceNotFoundException exception = assertThrows(
                    ResourceNotFoundException.class, () -> playerService.getPlayerById(999));

            // Verify the error message is correct
            assertTrue(exception.getMessage().contains("999"));

            // Verify mapper was NEVER called since we threw before reaching it
            verify(playerMapper, never()).toDTO(any());
        }

    }

    @Nested
    @DisplayName("Insert player tests")
    class insertPlayerTests{

        @Test
        @DisplayName("should save player and return DTO when no team")
        void insertPlayer_withoutTeam_savesAndReturnsDTO() {
            // ARRANGE
            when(playerMapper.toEntity(playerDTO)).thenReturn(player);
            when(playerRepository.save(player)).thenReturn(player);
            when(playerMapper.toDTO(player)).thenReturn(playerDTO);

            // ACT
            PlayerDTO result = playerService.insertPlayer(playerDTO);

            // ASSERT
            assertNotNull(result);
            assertEquals("Virat Kohli", result.getName());

            // VERIFY — teamRepository should never be called when no teamId
            verify(teamRepository, never()).findById(any());
            verify(playerRepository).save(player);
        }

        @Test
        @DisplayName("should assign team when teamId is provided")
        void insertPlayer_withTeamId_assignsTeamToPlayer() {
            // ARRANGE
            Team team = new Team();
            team.setId(1);
            team.setName("India");

            PlayerDTO dtoWithTeam = new PlayerDTO(1, "Virat Kohli", 500, 12000, 65.67, 1);

            when(playerMapper.toEntity(dtoWithTeam)).thenReturn(player);
            when(teamRepository.findById(1)).thenReturn(Optional.of(team));
            when(playerRepository.save(player)).thenReturn(player);
            when(playerMapper.toDTO(player)).thenReturn(dtoWithTeam);

            // ACT
            PlayerDTO result = playerService.insertPlayer(dtoWithTeam);

            // ASSERT
            assertNotNull(result);

            // VERIFY — team was looked up and set on player
            verify(teamRepository).findById(1);

            ArgumentCaptor<Player> captor = ArgumentCaptor.forClass(Player.class);
            verify(playerRepository).save(captor.capture());
            assertEquals(team, captor.getValue().getTeam());
        }

        @Test
        @DisplayName("should throw exception when teamId not found")
        void insertPlayer_withInvalidTeamId_throwsResourceNotFoundException() {
            // ARRANGE
            PlayerDTO dtoWithInvalidTeam = new PlayerDTO(1, "Virat", 500, 12000, 65.67, 999);

            when(playerMapper.toEntity(dtoWithInvalidTeam)).thenReturn(player);
            when(teamRepository.findById(999)).thenReturn(Optional.empty());

            // ACT + ASSERT
            assertThrows(ResourceNotFoundException.class, () -> playerService.insertPlayer(dtoWithInvalidTeam));

            // Player should never be saved if team not found
            verify(teamRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Remove player tests")
    class RemovePlayerTests{
        @Test
        @DisplayName("should delete player when found")
        void removePlayer_withValidId_deletesPlayer(){
            when(playerRepository.findById(1)).thenReturn(Optional.of(player));

            playerService.removePlayer(1);

            verify(playerRepository).delete(player);
        }

        @Test
        @DisplayName("should throw exception when player not found")
        void removePlayer_withInvalidId_throwsResourceNotFoundException() {
            when(playerRepository.findById(999)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> playerService.removePlayer(999));

            verify(teamRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("getAllPlayers tests")
    class GetAllPlayersTests {
        @Test
        @DisplayName("should return paginated PlayerDTOs")
        void getAllPlayers_returnsPagedPlayerDTOs() {

            // ARRANGE
            Pageable pageable = PageRequest.of(0, 1);

            Page<Player> playerPage = new PageImpl<>(List.of(player));

            when(playerRepository.findAll(pageable)).thenReturn(playerPage);
            when(playerMapper.toDTO(player)).thenReturn(playerDTO);

            // ACT
            Page<PlayerDTO> result = playerService.getAllPlayers(pageable);

            // ASSERT
            assertNotNull(result);
            assertEquals(1, result.getContent().size());
            assertEquals("Virat Kohli", result.getContent().get(0).getName());

            // VERIFY
            verify(playerRepository).findAll(pageable);
        }

        @Test
        @DisplayName("should return list of PlayerDTOs")
        void getAllPlayers_returnsListOfPlayerDTOs() {
            List<Player> playerList  = List.of(player);
            List<PlayerDTO> playerDTOList  = List.of(playerDTO);
            when(playerRepository.findAll()).thenReturn(playerList);
            when(playerMapper.toDTOList(playerList)).thenReturn(playerDTOList);

            List<PlayerDTO> resultList = playerService.getAllPlayers();

            assertNotNull(resultList);
            assertEquals(1,resultList.size());
            assertEquals("Virat Kohli", resultList.get(0).getName());
            verify(playerRepository).findAll();
        }
    }



}
