class Alc < ActiveRecord::Base
	validates :user_id, presence: true
	validates :note_id, presence: true
	belongs_to :user
	belongs_to :note
	attr_accessor :user_name
end
