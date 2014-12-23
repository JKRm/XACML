package org.cas.iie.idp.authenticate.Impl;


import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import org.cas.iie.idp.authenticate.IGetUser;
import org.cas.iie.idp.authenticate.ldap.LDAPhelper;
import org.cas.iie.idp.log.Logger;
import org.cas.iie.idp.user.UserRole;


public class GetUserByLdap implements IGetUser {

	private LDAPhelper ldaphelper = null;
	
	public GetUserByLdap() {
		
		ldaphelper = new LDAPhelper();
	}
	@Override
	public UserRole getUserByName(String username, boolean flag) {
		// TODO Auto-generated method stub
		UserRole user = new UserRole();
		
		user.setUsername(username);
		
	    String base = "ou=member";
	    
        String filter = "(&(objectClass=inetOrgPerson)(cn={0}))";
        
        String[] returnAttr = new String[] {"cn","uid","sn"};
        
        try {
        	
			NamingEnumeration enm = ldaphelper.search(base, filter, new String[] { username }, returnAttr);
			
			if(enm == null){
				
				throw new NamingException("search failed");
				
			}
			if(enm.hasMore()){
				
				SearchResult entry = (SearchResult)enm.next();
				
				String userDN = entry.getNameInNamespace();
				
				user.setUserDN(userDN);
				
				Attributes attrs = entry.getAttributes();
		
				if(attrs == null ||enm.hasMore()){
					
					throw new NamingException("search failed");
				}
				
				Attribute snAttr  = attrs.get("sn");
				
				Attribute uidAttr = attrs.get("uid");
				
				if(snAttr != null){
					
					user.setRealname(snAttr.get().toString());
					
				}
				if(uidAttr != null){
					
					user.setUserID(new Integer(uidAttr.get().toString()));
				}
				
				System.out.println("username="+user.getUsername());
			}
			
			user = getUserGroup(user);
			
        } catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			Logger.writelog(e);
			
			user = null;
		}
        
        ldaphelper.close();
        
		return user;
	}

	public UserRole getUserGroup(UserRole user){
		
	    String base = "ou=group";
	    
        String filter = "(&(objectClass=groupOfUniqueNames)(uniqueMember={0}))";
        
        String[] returnAttr = new String[] {"cn","uniqueMember"};
        
        try {
			NamingEnumeration enm = ldaphelper.search(base, filter, new String[] { user.getUserDN() }, returnAttr);
			
			if(enm == null){
				
				throw new NamingException("search failed");
			}
			
			while(enm.hasMore()){
				
				SearchResult entry = (SearchResult)enm.next();
				
				Attributes attrs = entry.getAttributes();

				Attribute cnAttr = attrs.get("cn");
				
				user.addUsergroup(cnAttr.get().toString());
			}
        } catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			Logger.writelog(e);
		}
        return user;
	}
	@Override
	public UserRole getUserByID(int userID, boolean flag) {
		// TODO Auto-generated method stub
		return null;
	}

}
