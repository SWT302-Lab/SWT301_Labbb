/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author Laptop Acer
 */

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.sql.*;
import java.util.List;
import model.Account;
import model.Staff;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.Spy;

public class StaffDAOTest {

    private StaffDAO staffDAO;
     
    @Mock
    private Connection mockConnection;
     
    @Spy
    private StaffDAO spyStaffDAO = new StaffDAO(); // Dùng Spy để mock một phần của đối tượng
    
    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        staffDAO = new StaffDAO();
        staffDAO.connection = mockConnection; // Giả lập kết nối database
    }

    @Test
    public void testGetAll_ShouldReturnStaffList() throws Exception {
        // Giả lập dữ liệu từ database
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        
        // Giả lập có 1 dòng dữ liệu
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt(1)).thenReturn(1);
        when(mockResultSet.getInt(2)).thenReturn(10); // Giả định ID tài khoản
        when(mockResultSet.getString(3)).thenReturn("Nguyễn Văn A");
        when(mockResultSet.getString(4)).thenReturn("Kế Toán");

        // Gọi phương thức cần test
        List<Staff> staffList = staffDAO.getAll();

        // Kiểm tra kết quả
        assertNotNull(staffList);
        assertEquals(1, staffList.size());
        assertEquals("Nguyễn Văn A", staffList.get(0).getStaffName());
        assertEquals("Kế Toán", staffList.get(0).getStaffRole());
    }

    @Test
    public void testGetAll_ShouldReturnEmptyList_WhenNoData() throws Exception {
        // Giả lập trường hợp không có dữ liệu
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // Không có dòng nào

        List<Staff> staffList = staffDAO.getAll();

        // Kiểm tra danh sách rỗng
        assertNotNull(staffList);
        assertTrue(staffList.isEmpty());
    }

    @Test
    public void testGetAll_ShouldHandleSQLException() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        List<Staff> staffList = staffDAO.getAll();

        // Đảm bảo khi lỗi xảy ra, danh sách trả về không null mà là danh sách rỗng
        assertNotNull(staffList);
        assertTrue(staffList.isEmpty());
    }
    
    @Test
public void testFindId_ShouldReturnStaff_WhenIdExists() throws Exception {
    int staffId = 1;
    String staffName = "Nguyễn Văn B";
    String staffRole = "Nhân viên IT";
    int accountId = 20;

    // Giả lập truy vấn SQL
    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    
    // Giả lập có 1 nhân viên trong database với ID = 1
    when(mockResultSet.next()).thenReturn(true);
    when(mockResultSet.getInt(1)).thenReturn(staffId);
    when(mockResultSet.getInt(2)).thenReturn(accountId);
    when(mockResultSet.getString(3)).thenReturn(staffName);
    when(mockResultSet.getString(4)).thenReturn(staffRole);

    // Giả lập phương thức getAccountByAccountId (giả định nó trả về một tài khoản hợp lệ)
    Account mockAccount = new Account(accountId, "user123", "123", "bồi bàn");
    StaffDAO spyStaffDAO = spy(staffDAO);
    doReturn(mockAccount).when(spyStaffDAO).getAccountByAccountId(accountId);

    // Gọi phương thức cần kiểm tra
    Staff result = spyStaffDAO.findId(staffId);

    // Kiểm tra kết quả
    assertNotNull(result);
    assertEquals(staffId, result.getStaffId());
    assertEquals(staffName, result.getStaffName());
    assertEquals(staffRole, result.getStaffRole());
    assertNotNull(result.getAccount());
    assertEquals(accountId, result.getAccount().getAccountID());
}

@Test
public void testFindId_ShouldReturnNull_WhenIdDoesNotExist() throws Exception {
    int staffId = 999; // Giả lập một ID không tồn tại

    // Giả lập truy vấn SQL
    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

    // Giả lập không có dữ liệu nào được trả về
    when(mockResultSet.next()).thenReturn(false);

    // Gọi phương thức cần kiểm tra
    Staff result = staffDAO.findId(staffId);

    // Kiểm tra kết quả phải là null
    assertNull(result);
}

@Test
public void testFindId_ShouldHandleSQLException() throws Exception {
    int staffId = 1;

    // Giả lập lỗi SQL khi thực hiện truy vấn
    when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

    // Gọi phương thức cần kiểm tra
    Staff result = staffDAO.findId(staffId);

    // Kiểm tra kết quả phải là null
    assertNull(result);
}

@Test
    public void testInsert_ShouldInsertStaffSuccessfully() throws Exception {
        // Tạo dữ liệu đầu vào
        Account mockAccount = new Account(10, "user123", "123", "Quản lý");
        Staff mockStaff = new Staff(1, "Nguyễn Văn A", "Nhân viên IT", mockAccount);

        // Giả lập PreparedStatement
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Gọi phương thức cần test
        staffDAO.insert(mockStaff);

        // Kiểm tra xem các phương thức được gọi đúng cách
        verify(mockPreparedStatement, times(1)).setInt(1, mockAccount.getAccountID());
        verify(mockPreparedStatement, times(1)).setString(2, mockStaff.getStaffName());
        verify(mockPreparedStatement, times(1)).setString(3, mockStaff.getStaffRole());
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testInsert_ShouldHandleSQLException() throws Exception {
        // Tạo dữ liệu đầu vào
        Account mockAccount = new Account(10, "user123", "123", "Quản lý");
        Staff mockStaff = new Staff(1, "Nguyễn Văn A", "Nhân viên IT", mockAccount);

        // Giả lập lỗi SQL
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Lỗi kết nối database"));

        // Gọi phương thức cần test (không cần kiểm tra kết quả vì phương thức không trả về gì)
        staffDAO.insert(mockStaff);

        // Kiểm tra không có lỗi ném ra (nếu có lỗi, test sẽ fail)
    }
    
    @Test
    public void testUpdate_ShouldUpdateStaffSuccessfully() throws Exception {
        // Tạo dữ liệu đầu vào
        Account mockAccount = new Account(10, "user123", "123", "Quản lý");
        Staff mockStaff = new Staff(1, "Nguyễn Văn B", "Nhân viên IT", mockAccount);

        // Giả lập PreparedStatement
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Gọi phương thức cần test
        staffDAO.update(mockStaff);

        // Kiểm tra xem các phương thức được gọi đúng cách
        verify(mockPreparedStatement, times(1)).setString(1, mockStaff.getStaffName());
        verify(mockPreparedStatement, times(1)).setString(2, mockStaff.getStaffRole());
        verify(mockPreparedStatement, times(1)).setInt(3, mockStaff.getStaffId());
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testUpdate_ShouldHandleSQLException() throws Exception {
        // Tạo dữ liệu đầu vào
        Account mockAccount = new Account(10, "user123", "123", "Quản lý");
        Staff mockStaff = new Staff(1, "Nguyễn Văn B", "Nhân viên IT", mockAccount);

        // Giả lập lỗi SQL
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Lỗi cập nhật dữ liệu"));

        // Gọi phương thức cần test (không cần kiểm tra kết quả vì phương thức không trả về gì)
        staffDAO.update(mockStaff);

        // Kiểm tra không có lỗi ném ra (nếu có lỗi, test sẽ fail)
    }
    
    @Test
    public void testDelete_ShouldDeleteStaffSuccessfully() throws Exception {
        // Giả lập ID nhân viên cần xóa
        int staffId = 1;

        // Giả lập PreparedStatement
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Gọi phương thức cần test
        staffDAO.delete(staffId);

        // Kiểm tra xem phương thức setInt() và executeUpdate() có được gọi đúng không
        verify(mockPreparedStatement, times(1)).setInt(1, staffId);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testDelete_ShouldHandleSQLException() throws Exception {
        // Giả lập ID nhân viên cần xóa
        int staffId = 1;

        // Giả lập lỗi SQL
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Lỗi xóa dữ liệu"));

        // Gọi phương thức cần test (không cần kiểm tra kết quả vì phương thức không trả về gì)
        staffDAO.delete(staffId);

        // Kiểm tra không có lỗi ném ra (nếu có lỗi, test sẽ fail)
    }
}

