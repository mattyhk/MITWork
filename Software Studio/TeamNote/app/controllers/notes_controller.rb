class NotesController < ApplicationController
  before_action :set_note, only: [:show, :edit, :update, :destroy]

  # GET /notes
  # GET /notes.json
  def index
    @authoredNotes = current_user.notes
    @viewNotes = []
    @editNotes = []
    @authoredNotes.each do |note|
      alcs = Alc.where(note_id: note.id, user_id: current_user.id)
      alcs.each do |alc|
        if alc.write
          @editNotes.push(note)
        else
          @viewNotes.push(note)
        end
      end
    end

    # @permissions = current_user.alcs

    # @editableNotes = []
    # Alc.where(user_id: current_user.id, write: true).find_each do |permission|
    #   @editableNotes.push(Note.find(permission.node_id))
    # end

  end

  # GET /notes/1
  # GET /notes/1.json
  def show
    @editors = []
    @viewers = []
    for alc in @note.alcs
      if alc.write
        @editors.push(User.find(alc.user_id).user_name)
      else
        @viewers.push(User.find(alc.user_id).user_name)
      end
    end

    respond_to do |format|
      format.html {}
      format.js {}
      format.json {render :json => { :content => @note.content } }
    end

  end

  # # GET /notes/new
  def new
    @note = Note.new
    @note.alcs.build
  end

  # # GET /notes/1/edit
  def edit
    puts params
  end

  # POST /notes
  # POST /notes.json
  def create
    @note = Note.new(note_params.merge(:user_id => current_user.id))
    respond_to do |format|
      if @note.save
        author = Alc.create(user_id: current_user.id, note_id: @note.id, read: true, write: true)
        format.html { redirect_to @note, notice: 'Note was successfully created.' } 
        format.json { render action: 'show', status: :created, location: @note }
        format.js {}
      else
        format.html { render action: 'new' }
        format.json { render json: @note.errors, status: :unprocessable_entity }
      end
    end
  end

  # # PATCH/PUT /notes/1
  # # PATCH/PUT /notes/1.json
  def update
    respond_to do |format|
      if @note.update(note_params)
        format.html { redirect_to @note, notice: 'Note was successfully updated.' }
        format.json { head :no_content }  
      else
        format.html { render action: 'edit' }
        format.json { render json: @note.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /notes/1
  # DELETE /notes/1.json
  def destroy
    @note.destroy
    respond_to do |format|
      format.html { redirect_to notes_url }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_note
      @note = Note.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def note_params
      params.require(:note).permit(:subject, :content, 
        :user_id, alcs_attributes: [:user_id, :read, :write, :note_id, :id, :_destroy])
    end

    def current_user
      @current_user ||= User.find(session[:user_id]) if session[:user_id]
    end
end
