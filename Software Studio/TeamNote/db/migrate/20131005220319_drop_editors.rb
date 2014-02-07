class DropEditors < ActiveRecord::Migration
  def up
  	drop_table :editors
  	drop_table :viewers
  end
end
