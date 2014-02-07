class AlcsController < ApplicationController
  before_action :set_alc, only: [:show, :edit, :update, :destroy]

  # GET /alcs
  # GET /alcs.json
  def index
    @alcs = Alc.all
  end

  # GET /alcs/1
  # GET /alcs/1.json
  def show
  end

  # GET /alcs/new
  def new
    @alc = Alc.new
    @note_id = params[:note_id]
  end

  # GET /alcs/1/edit
  def edit
  end

  # POST /alcs
  # POST /alcs.json
  def create
    givenUserName = params[:alc][:user_name]
    user = User.where(user_name: givenUserName).take
    @alc = Alc.new(alc_params.merge(:user_id => user.id))

    respond_to do |format|
      if @alc.save
        format.html { redirect_to notes_path, notice: 'Permission was successfully created.' }
        format.json { render action: 'show', status: :created, location: @alc }
      else
        format.html { render action: 'new' }
        format.json { render json: @alc.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /alcs/1
  # PATCH/PUT /alcs/1.json
  def update
    respond_to do |format|
      if @alc.update(alc_params)
        format.html { redirect_to @alc, notice: 'Alc was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: 'edit' }
        format.json { render json: @alc.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /alcs/1
  # DELETE /alcs/1.json
  def destroy
    @alc.destroy
    respond_to do |format|
      format.html { redirect_to alcs_url }
      format.json { head :no_content }
    end
  end


  private
    # Use callbacks to share common setup or constraints between actions.
    def set_alc
      @alc = Alc.find(params[:id])
    end

    def current_note
    @current_note ||= Note.find(note[:id]) if note[:id]
  end

    # Never trust parameters from the scary internet, only allow the white list through.
    def alc_params
      params.require(:alc).permit(:new, :user_id, :note_id, :read, :write, :user_name)
    end
end
