package org.skife.jdbi.v2.sqlobject;

import com.fasterxml.classmate.members.ResolvedMethod;
import org.skife.jdbi.v2.ConcreteStatementContext;
import org.skife.jdbi.v2.Update;

class UpdateHandler extends CustomizingStatementHandler
{
    final String sql;

    public UpdateHandler(Class<?> sqlObjectType, ResolvedMethod method)
    {
        super(sqlObjectType, method);
        this.sql = SqlObject.getSql(method.getRawMember().getAnnotation(SqlUpdate.class), method.getRawMember());
    }

    public Object invoke(HandleDing h, Object target, Object[] args)
    {
        Update q = h.getHandle().createStatement(sql);
        populateSqlObjectData((ConcreteStatementContext)q.getContext());
        applyBinders(q, args);
        applyCustomizers(q, args);
        return q.execute();
    }
}