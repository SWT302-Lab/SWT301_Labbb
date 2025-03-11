package dal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import model.Table;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TableDAOTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private TableDAO tableDAO;

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    /** ✅ Test: Lấy trạng thái bàn thành công */
    @Test
    public void testGetTableStatusById_Success() throws SQLException {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("TableStatus")).thenReturn("Available");

        String status = tableDAO.getTableStatusById(1);

        assertEquals("Available", status);
        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).executeQuery();
    }

    /** ✅ Test: Lấy trạng thái bàn nhưng không tìm thấy */
    @Test
    public void testGetTableStatusById_NotFound() throws SQLException {
        when(resultSet.next()).thenReturn(false);

        String status = tableDAO.getTableStatusById(999);

        assertEquals("", status);
        verify(preparedStatement).setInt(1, 999);
        verify(preparedStatement).executeQuery();
    }

    /** ✅ Test: Lấy tất cả bàn */
    @Test
    public void testGetAllTable() throws SQLException {
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("TableID")).thenReturn(1, 2);
        when(resultSet.getString("TableStatus")).thenReturn("Available", "Occupied");

        List<Table> tables = tableDAO.getAllTable();

        assertEquals(2, tables.size());
        assertEquals(1, tables.get(0).getId());
        assertEquals("Available", tables.get(0).getStatus());
        assertEquals(2, tables.get(1).getId());
        assertEquals("Occupied", tables.get(1).getStatus());

        verify(preparedStatement).executeQuery();
    }

    /** ✅ Test: Thêm bàn mới */
    @Test
    public void testInsertTable() throws SQLException {
        Table table = new Table(1, "Available");

        tableDAO.insertTable(table);

        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).setString(2, "Available");
        verify(preparedStatement).executeUpdate();
    }

    /** ✅ Test: Tìm bàn theo ID (Có bàn) */
    @Test
    public void testFindTableById_Found() throws SQLException {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);
        when(resultSet.getString(2)).thenReturn("Available");

        Table table = tableDAO.findTableById(1);

        assertNotNull(table);
        assertEquals(1, table.getId());
        assertEquals("Available", table.getStatus());

        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).executeQuery();
    }

    /** ✅ Test: Tìm bàn theo ID (Không tìm thấy) */
    @Test
    public void testFindTableById_NotFound() throws SQLException {
        when(resultSet.next()).thenReturn(false);

        Table table = tableDAO.findTableById(999);

        assertNull(table);
        verify(preparedStatement).setInt(1, 999);
        verify(preparedStatement).executeQuery();
    }

    /** ✅ Test: Xóa bàn thành công */
    @Test
    public void testDeleteTable_Success() throws SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        tableDAO.deleteTable(1);

        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).executeUpdate();
    }

    /** ✅ Test: Cập nhật trạng thái bàn */
    @Test
    public void testUpdateTable() throws SQLException {
        Table table = new Table(1, "Occupied");

        tableDAO.updateTable(table);

        verify(preparedStatement).setString(1, "Occupied");
        verify(preparedStatement).setInt(2, 1);
        verify(preparedStatement).executeUpdate();
    }

    /** ✅ Test: Đặt trạng thái bàn */
    @Test
    public void testSetStatus() throws SQLException {
        tableDAO.setStatsus(1, "Reserved");

        verify(preparedStatement).setString(1, "Reserved");
        verify(preparedStatement).setInt(2, 1);
        verify(preparedStatement).executeUpdate();
    }

    /** ✅ Test: Kiểm tra bàn có bị chiếm không (Occupied) */
    @Test
    public void testCheckIfTableOccupied_True() throws SQLException {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("TableStatus")).thenReturn("occupied");

        boolean result = tableDAO.checkIfTableOccupied(1);

        assertTrue(result);
        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).executeQuery();
    }

    /** ✅ Test: Kiểm tra bàn có bị chiếm không (Available) */
    @Test
    public void testCheckIfTableOccupied_False() throws SQLException {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("TableStatus")).thenReturn("available");

        boolean result = tableDAO.checkIfTableOccupied(1);

        assertFalse(result);
        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).executeQuery();
    }

    /** ✅ Test: Kiểm tra bàn có bị chiếm không (Không tìm thấy) */
    @Test
    public void testCheckIfTableOccupied_NotFound() throws SQLException {
        when(resultSet.next()).thenReturn(false);

        boolean result = tableDAO.checkIfTableOccupied(999);

        assertFalse(result);
        verify(preparedStatement).setInt(1, 999);
        verify(preparedStatement).executeQuery();
    }
}