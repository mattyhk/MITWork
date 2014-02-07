class SessionsController < ApplicationController
  def new
  end

  def create
  	user = User.authenticate(params[:user_name], params[:password])
  	if user
    	session[:user_id] = user.id
    	redirect_to notes_url, :notice => "Logged In!"
  	else
    	flash.now.alert = "Invalid Username or Password"
    	render "new"
  	end
  end

  def destroy
  	session[:user_id] = nil
  	redirect_to root_url, :notice => "Logged out!"
	end

end
