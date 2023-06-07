package aaa.bbb.ccc.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

public class ResultSetIterator implements Iterator<ResultSet>, Iterable<ResultSet>
{
	private final ResultSet rs;
	private int pageIndex;
	private int pageSize;
	private int totalRow;
	private int startRowIndex;
	private int endRowIndex;
	private int totalPage;
	private boolean wentNext = false;

	public ResultSetIterator(ResultSet rs)
			throws SQLException
	{
		this(rs, 0, 0);
	}

	public ResultSetIterator(ResultSet rs, int pageIndex, int pageSize)
			throws SQLException
	{
		this.rs = rs;
		this.pageIndex = Math.max(pageIndex, 1);
		this.pageSize = Math.max(pageSize, 0);

		rs.last();
		this.totalRow = rs.getRow();
		rs.beforeFirst();

		if(pageSize == 0)
		{
			this.pageIndex = 1;
			this.startRowIndex = 1;
			this.endRowIndex = this.totalRow;
			this.totalPage = 1;
		}
		else
		{
			this.startRowIndex = (this.pageIndex - 1) * this.pageSize + 1;
			this.endRowIndex = this.startRowIndex + pageSize - 1;
			this.totalPage = (int) Math.ceil(this.totalRow / (double) this.pageSize);

			if(this.startRowIndex > 1 && this.startRowIndex <= this.totalRow)
			{
				rs.absolute(this.startRowIndex);
				this.wentNext = true;
			}
		}
	}

	public boolean nextPhysicalItemAvailable()
	{
		return this.wentNext || hasNext();
	}

	@Override
	public boolean hasNext()
	{
		try
		{
			if(!this.wentNext)
				this.wentNext = this.rs.next();
			int rowIndex = this.rs.getRow();
			return this.wentNext && rowIndex >= this.startRowIndex
					&& rowIndex <= this.endRowIndex && rowIndex <= this.totalRow;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace(System.out);
		}
		return false;
	}

	@Override
	public ResultSet next()
	{
		if(!hasNext())
			return null;
		this.wentNext = false;
		return this.rs;
	}

	@Override
	public void remove()
	{
		throw new IllegalAccessError("Function is not implemented");
	}

	@Override
	public Iterator iterator()
	{
		return this;
	}

	public ResultSet getRs()
	{
		return rs;
	}

	public int getPageIndex()
	{
		return pageIndex;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public int getTotalRow()
	{
		return totalRow;
	}

	public int getStartRowIndex()
	{
		return startRowIndex;
	}

	public int getTotalPage()
	{
		return totalPage;
	}

	public int getEndRowIndex()
	{
		return endRowIndex;
	}

	public boolean isWentNext()
	{
		return wentNext;
	}
}
