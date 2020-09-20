package com.paymybuddy.exchange.integration.services;

import com.paymybuddy.exchange.dao.DAOFactory;
import com.paymybuddy.exchange.dao.TransactionDAO;
import com.paymybuddy.exchange.dao.UserDAO;
import com.paymybuddy.exchange.integration.config.DataBaseTestConfig;
import com.paymybuddy.exchange.models.Transaction;
import com.paymybuddy.exchange.models.User;
import com.paymybuddy.exchange.models.UserRelationship;
import com.paymybuddy.exchange.services.TransactionService;
import com.paymybuddy.exchange.services.UserRelationshipService;
import com.paymybuddy.exchange.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest( DAOFactory.class )
public class TransactionServiceIT {

    TransactionDAO transactionDAO = new TransactionDAO();
    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static DataBasePrepareService dataBasePrepareService = new DataBasePrepareService();

    @Mock
    UserRelationshipService userRelationshipService;

    @Mock
    UserService userService;

    @InjectMocks
    TransactionService transactionService = new TransactionService();

    @Test
    public void testCreateTransaction() throws SQLException {
        dataBasePrepareService.clearDataBaseEntries();
        transactionDAO.dataBaseConfig = dataBaseTestConfig;
        //given

        User user = new User("test","DuCreateService","autre@mail.com",12.0,"je fais un test du read");
        user.setId(2);
        User user2 = new User("test","DuCreateService","autre@mail.com",12.0,"je fais un test du read");
        user.setId(22);
        Transaction transaction = new Transaction(18.0,2,22,2,"notre type");

        mockStatic(DAOFactory.class);
        //WHEN
        PowerMockito.when(DAOFactory.getTransactionDAO()).thenReturn(transactionDAO);
        Mockito.when(userRelationshipService.verifyRelationship(2,22)).thenReturn(true);
        Mockito.when(userService.read(2)).thenReturn(user);
        Mockito.when(userService.read(22)).thenReturn(user2);
        //THEN
        assertTrue(transactionService.create(transaction));
        assertTrue(getTransactionByIdUserAndSender(2,22).getFees()==0.9);
    }


    private Transaction getTransactionByIdUserAndSender(int idUserSender, int idUserReceiver){
        for (Transaction transaction: transactionDAO.listAll()) {
            if (transaction.getIdUserSender()==idUserSender && transaction.getIdUserReceiver()==idUserReceiver)
                return transaction;
        }
        return null;
    }

}
